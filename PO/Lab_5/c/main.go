package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func main() {
	rand.Seed(time.Now().UnixNano())
	var wg sync.WaitGroup
	wg.Add(3)
	var a, b, c [10]int
	for i := 0; i < 10; i++ {
		a[i] = rand.Intn(10)
		b[i] = rand.Intn(10)
		c[i] = rand.Intn(10)
	}
	go func() {
		defer wg.Done()
		for {
			sum := 0
			for i := 0; i < 10; i++ {
				sum += a[i]
			}
			if sum == 50 {
				fmt.Println("a = ", a)
				break
			} else {
				a[rand.Intn(10)] += rand.Intn(2)*2 - 1
			}
		}
	}()
	go func() {
		defer wg.Done()
		for {
			sum := 0
			for i := 0; i < 10; i++ {
				sum += b[i]
			}
			if sum == 50 {
				fmt.Println("b = ", b)
				break
			} else {
				b[rand.Intn(10)] += rand.Intn(2)*2 - 1
			}
		}
	}()
	go func() {
		defer wg.Done()
		for {
			sum := 0
			for i := 0; i < 10; i++ {
				sum += c[i]
			}
			if sum == 50 {
				fmt.Println("c = ", c)
				break
			} else {
				c[rand.Intn(10)] += rand.Intn(2)*2 - 1
			}
		}
	}()
	wg.Wait()
}
