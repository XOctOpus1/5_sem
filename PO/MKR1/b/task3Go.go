// Denys Gordiichuk IPS-31 var#3

package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var (
	//mutex for parking
	mutex = &sync.Mutex{}
	//mutex for car
	mutexCar = &sync.Mutex{}
	//parking
	parking = make([]bool, 5)
	//cars
	cars = make([]int, 10)
	//time
	t = time.Now()
)

func main() {
	//init cars
	for i := 0; i < 10; i++ {
		cars[i] = i
	}
	//init parking
	for i := 0; i < 5; i++ {
		parking[i] = false
	}
	//start cars
	for i := 0; i < 10; i++ {
		go car(cars[i])
	}
	//wait for cars
	time.Sleep(5 * time.Second)
}

func car(id int) {
	//wait for parking
	for {
		//check if car is parked
		mutexCar.Lock()
		if parking[id%5] == false {
			//parking
			mutexCar.Unlock()
			mutex.Lock()
			parking[id%5] = true
			mutex.Unlock()
			fmt.Printf("Car %d parked at %d\n", id, id%5)
			break
		}
		mutexCar.Unlock()
		//wait for parking
		time.Sleep(time.Duration(rand.Intn(1000)) * time.Millisecond)
	}
	//wait for parking
	time.Sleep(time.Duration(rand.Intn(1000)) * time.Millisecond)
	//leave parking
	mutex.Lock()
	parking[id%5] = false
	mutex.Unlock()
	fmt.Printf("Car %d left at %d\n", id, id%5)
}
