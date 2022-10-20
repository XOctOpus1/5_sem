//Denys Gordiichuk IPS-31
//Lab1 var13


// Using C ++ , threads , condvars and global vars or attributes to pass the result
// of function computation to  to correctly organize computation using multiple processes

#include <iostream>
#include <variant>
#include <mutex>
#include <condition_variable>
#include <thread>
#include <unistd.h>
#include <fcntl.h>

namespace compfuncs {
    enum INT_FUNC {
        INT_SUM,
        INT_MULT,
        INT_FACT
    };

    template <INT_FUNC F>
    struct trial_f;

    template <>
    struct trial_f<INT_SUM> {
        int operator()(int x) {
            int sum = 0;
            for (int i = 1; i <= x; i++) {
                sum += i;
            }
            return sum;
        }
    };

    template <>
    struct trial_f<INT_MULT> {
        int operator()(int x) {
            int mult = 1;
            for (int i = 1; i <= x; i++) {
                mult *= i;
            }
            return mult;
        }
    };

    template <>
    struct trial_f<INT_FACT> {
        int operator()(int x) {
            int fact = 1;
            for (int i = 1; i <= x; i++) {
                fact *= i;
            }
            return fact;
        }
    };
}

namespace manager {
    class Manager {
    private:
        std::string threadB;
        std::string threadF;
        std::string threadG;
        std::string threaddB;
        int fdB;
        int fdF;
        int fdG;
        int fddB;
        int x;
        std::variant<int, std::string> y;
        std::mutex m;
        std::condition_variable cv;
        bool ready;
        bool processed;
        bool hardFail;

        void createFO(std::string thread) {
            std::string filename = thread + ".txt";
            int fd = open(filename.c_str(), O_CREAT | O_RDWR, 0666);
            if (fd == -1) {
                std::cout << "Error creating file " << filename << std::endl;
                exit(1);
            }
            if (thread == threadB) {
                fdB = fd;
            }
            else if (thread == threadF) {
                fdF = fd;
            }
            else if (thread == threadG) {
                fdG = fd;
            }
            else if (thread == threaddB) {
                fddB = fd;
            }
        }


        void openFO(std::string name) {
            if (name == "B") {
                fdB = open(name.c_str(), O_RDONLY);
            }
            else if (name == "F") {
                fdF = open(name.c_str(), O_RDONLY);
            }
            else if (name == "G") {
                fdG = open(name.c_str(), O_RDONLY);
            }
            else if (name == "dB") {
                fddB = open(name.c_str(), O_WRONLY);
            }
        }

        void readFO(std::string name) {
            if (name == "B") {
                read(fdB, &x, sizeof(x));
            }
            else if (name == "F") {
                read(fdF, &x, sizeof(x));
            }
            else if (name == "G") {
                read(fdG, &x, sizeof(x));
            }
        }

        void writeFO(std::string name) {
            if (name == "dB") {
                write(fddB, &y, sizeof(y));
            }
        }

        void closeFO(std::string name) {
            if (name == "B") {
                close(fdB);
            }
            else if (name == "F") {
                close(fdF);
            }
            else if (name == "G") {
                close(fdG);
            }
            else if (name == "dB") {
                close(fddB);
            }
        }

        bool isHardFail(std::variant<int, std::string> y) {
            if (std::holds_alternative<std::string>(y)) {
                return true;
            }
            else {
                return false;
            }
        }

        void tB() {
            std::unique_lock<std::mutex> lk(m);
            cv.wait(lk, [this] { return ready; });
            if (x == 0) {
                hardFail = true;
                cv.notify_all();
                return;
            }
            y = compfuncs::trial_f<compfuncs::INT_SUM>()(x);
            processed = true;
            cv.notify_all();
        }

        void tF() {
            std::unique_lock<std::mutex> lk(m);
            cv.wait(lk, [this] { return ready; });
            if (x == 0) {
                hardFail = true;
                cv.notify_all();
                return;
            }
            y = compfuncs::trial_f<compfuncs::INT_MULT>()(x);
            processed = true;
            cv.notify_all();
        }

        void tG() {
            std::unique_lock<std::mutex> lk(m);
            cv.wait(lk, [this] { return ready; });
            if (x == 0) {
                hardFail = true;
                cv.notify_all();
                return;
            }
            y = compfuncs::trial_f<compfuncs::INT_FACT>()(x);
            processed = true;
            cv.notify_all();
        }

        void tdB() {
            std::unique_lock<std::mutex> lk(m);
            cv.wait(lk, [this] { return processed; });
            if (hardFail) {
                y = "Hard fail";
            }
            writeFO(threaddB);
            ready = false;
            processed = false;
            hardFail = false;
            cv.notify_all();
        }

    public:
        Manager(std::string threadB, std::string threadF, std::string threadG, std::string threaddB) {
            this->threadB = threadB;
            this->threadF = threadF;
            this->threadG = threadG;
            this->threaddB = threaddB;
            createFO(threadB);
            createFO(threadF);
            createFO(threadG);
            createFO(threaddB);
            openFO(threadB);
            openFO(threadF);
            openFO(threadG);
            openFO(threaddB);
            ready = false;
            processed = false;
            hardFail = false;
        }

        void run() {
            std::thread tB(&Manager::threadB, this);
            std::thread tF(&Manager::threadF, this);
            std::thread tG(&Manager::threadG, this);
            std::thread tdB(&Manager::threaddB, this);
            while (true) {
                readFO(threadB);
                if (x == 0) {
                    break;
                }
                readFO(threadF);
                readFO(threadG);
                ready = true;
                cv.notify_all();
                std::unique_lock<std::mutex> lk(m);
                cv.wait(lk, [this] { return !ready; });
                closeFO(threadB);
                closeFO(threadF);
                closeFO(threadG);
                closeFO(threaddB);
            }
            tB.join();
            tF.join();
            tG.join();
            tdB.join();
        }
    };
}



int main() {
    int x;
    std::cin >> x;
    std::cout << compfuncs::trial_f<compfuncs::INT_SUM>()(x) << std::endl;
    std::cout << compfuncs::trial_f<compfuncs::INT_MULT>()(x) << std::endl;
    std::cout << compfuncs::trial_f<compfuncs::INT_FACT>()(x) << std::endl;

    manager::Manager manager("B", "F", "G", "dB");
    manager.run();
    return 0;
}


