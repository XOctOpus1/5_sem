package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

const (
	// N - pot capacity
	N = 5
	// n - number of bees
	n = 3
)

var (
	// pot - pot of honey
	pot = 0
	// bear - bear
	bear = sync.NewCond(&sync.Mutex{})
	// bees - bees
	bees = make([]*sync.Cond, n)
)

func main() {
	for i := 0; i < n; i++ {
		bees[i] = sync.NewCond(&sync.Mutex{})
		go bee(i)
	}
	go bearFunc()
	time.Sleep(10 * time.Second)
}

func bearFunc() {
	for {
		bear.L.Lock()
		for pot != N {
			bear.Wait()
		}
		fmt.Println("Bear woke up")
		pot = 0
		fmt.Println("Bear ate honey")
		for i := 0; i < n; i++ {
			bees[i].Signal()
		}
		bear.L.Unlock()
	}
}

func bee(i int) {
	for {
		bees[i].L.Lock()
		for pot == N {
			bees[i].Wait()
		}
		pot++
		fmt.Printf("Bee %d collected honey, pot = %d\n", i, pot)
		if pot == N {
			bear.Signal()
		}
		bees[i].L.Unlock()
		time.Sleep(time.Duration(rand.Intn(1000)) * time.Millisecond)
	}
}

// Output:
// Bee 0 collected honey, pot = 1
// Bee 1 collected honey, pot = 2
// Bee 2 collected honey, pot = 3
// Bee 0 collected honey, pot = 4
// Bee 1 collected honey, pot = 5
// Bear woke up
