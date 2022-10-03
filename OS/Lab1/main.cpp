//Denys Gordiichuk IPS-31
//Lab1 var13


// Using C ++ , threads , condvars and global vars or attributes to pass the result
// of function computation to  to correctly organize computation using multiple processes

#include <iostream>
#include <string>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <signal.h>
#include <cstring>
#include <variant>
#include <thread>
#include <mutex>
#include <condition_variable>

namespace os {
    namespace lab1 {
        namespace compfuncs {
            struct hard_fail {};
            struct INT_SUM {
                int operator()(int x) {
                    if (x < 0)
                        return std::variant<hard_fail, int>(hard_fail());
                    int sum = 0;
                    for (int i = 0; i <= x; ++i)
                        sum += i;
                    return std::variant<hard_fail, int>(sum);
                }
            };
            struct INT_MULT {
                int operator()(int x) {
                    if (x < 0)
                        return std::variant<hard_fail, int>(hard_fail());
                    int mult = 1;
                    for (int i = 1; i <= x; ++i)
                        mult *= i;
                    return std::variant<hard_fail, int>(mult);
                }
            };
            struct INT_FACT {
                int operator()(int x) {
                    if (x < 0)
                        return std::variant<hard_fail, int>(hard_fail());
                    int fact = 1;
                    for (int i = 2; i <= x; ++i)
                        fact *= i;
                    return std::variant<hard_fail, int>(fact);
                }
            };
            template<typename T>
            std::variant<hard_fail, int> trial_f(int x) {
                return T()(x);
            }
        }
        class Manager {
        private:
            std::string Process;
            std::string ProcessF;
            std::string ProcessG;
            std::string ProcessB;
            int fo;
            int foF;
            int foG;
            int foB;
            int x;
            std::variant<compfuncs::hard_fail, int> y;
            std::mutex m;
            std::condition_variable cv;
            bool ready;
            bool processed;
            bool hardFail;
            bool isHardFail(std::variant<compfuncs::hard_fail, int> y) {
                return std::holds_alternative<compfuncs::hard_fail>(y);
            }
            void createFO(std::string Process) {
                unlink(Process.c_str());
                if (mkfifo(Process.c_str(), 1000) == -1)
                    std::cout << "Can't create fifo" << std::endl;
            }
            void openFO(std::string Process) {
                if (Process == ProcessF) {
                    foF = open(Process.c_str(), O_RDONLY);
                    if (foF == -1)
                        std::cout << "Can't open fifo" << std::endl;
                }
                else if (Process == ProcessG) {
                    foG = open(Process.c_str(), O_RDONLY);
                    if (foG == -1)
                        std::cout << "Can't open fifo" << std::endl;
                }
                else if (Process == ProcessB) {
                    foB = open(Process.c_str(), O_WRONLY);
                    if (foB == -1)
                        std::cout << "Can't open fifo" << std::endl;
                }
            }
            void closeFO(std::string Process) {
                if (Process == ProcessF) {
                    close(foF);
                }
                else if (Process == ProcessG) {
                    close(foG);
                }
                else if (Process == ProcessB) {
                    close(foB);
                }
            }
            void readFO(std::string Process) {
                if (Process == ProcessF) {
                    if (read(foF, &x, sizeof(int)) == -1)
                        std::cout << "Can't read fifo" << std::endl;
                }
                else if (Process == ProcessG) {
                    if (read(foG, &y, sizeof(y)) == -1)
                        std::cout << "Can't read fifo" << std::endl;
                }
            }
            void writeFO(std::string Process) {
                if (Process == ProcessB) {
                    if (write(foB, &y, sizeof(y)) == -1)
                        std::cout << "Can't write fifo" << std::endl;
                }
            }
            void process() {
                std::unique_lock<std::mutex> lk(m);
                cv.wait(lk, [this] { return ready; });
                if (isHardFail(y)) {
                    hardFail = true;
                    processed = true;
                    cv.notify_one();
                }
                else {
                    y = compfuncs::trial_f<compfuncs::INT_SUM>(std::get<int>(y));
                    processed = true;
                    cv.notify_one();
                }
            }
            void processF() {
                std::unique_lock<std::mutex> lk(m);
                cv.wait(lk, [this] { return ready; });
                if (isHardFail(y)) {
                    hardFail = true;
                    processed = true;
                    cv.notify_one();
                }
                else {
                    y = compfuncs::trial_f<compfuncs::INT_MULT>(std::get<int>(y));
                    processed = true;
                    cv.notify_one();
                }
            }
            void processG() {
                std::unique_lock<std::mutex> lk(m);
                cv.wait(lk, [this] { return ready; });
                if (isHardFail(y)) {
                    hardFail = true;
                    processed = true;
                    cv.notify_one();
                }
                else {
                    y = compfuncs::trial_f<compfuncs::INT_FACT>(std::get<int>(y));
                    processed = true;
                    cv.notify_one();
                }
            }
            void processB() {
                std::unique_lock<std::mutex> lk(m);
                cv.wait(lk, [this] { return ready; });
                if (isHardFail(y)) {
                    hardFail = true;
                    processed = true;
                    cv.notify_one();
                }
                else {
                    y = compfuncs::trial_f<compfuncs::INT_SUM>(std::get<int>(y));
                    processed = true;
                    cv.notify_one();
                }
            }
        public:
            Manager(std::string Process, std::string ProcessF, std::string ProcessG, std::string ProcessB) {
                this->Process = Process;
                this->ProcessF = ProcessF;
                this->ProcessG = ProcessG;
                this->ProcessB = ProcessB;
                ready = false;
                processed = false;
                hardFail = false;
            }
            void run() {
                createFO(Process);
                createFO(ProcessF);
                createFO(ProcessG);
                createFO(ProcessB);
                openFO(Process);
                openFO(ProcessF);
                openFO(ProcessG);
                openFO(ProcessB);
                std::thread t1(&Manager::process, this);
                std::thread t2(&Manager::processF, this);
                std::thread t3(&Manager::processG, this);
                std::thread t4(&Manager::processB, this);
                while (true) {
                    readFO(Process);
                    if (x == 0) {
                        break;
                    }
                    ready = true;
                    cv.notify_all();
                    std::unique_lock<std::mutex> lk(m);
                    cv.wait(lk, [this] { return processed; });
                    if (hardFail) {
                        break;
                    }
                    writeFO(ProcessB);
                    ready = false;
                    processed = false;
                }
                t1.join();
                t2.join();
                t3.join();
                t4.join();
                closeFO(Process);
                closeFO(ProcessF);
                closeFO(ProcessG);
                closeFO(ProcessB);
            }
        };
    }
    int main() {
        std::string Process = "B";
        std::string ProcessF = "F";
        std::string ProcessG = "G";
        std::string ProcessB = "B";
        manager::Manager manager(Process, ProcessF, ProcessG, ProcessB);
        manager.run();
        return 0;
    }
} 
