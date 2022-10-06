// Create an application with three threads. Each thread works with its own array, the threads check the sum of the elements of their array with the sums of the elements of other threads and stop when all three sums are equal to each other. If the sums are not equal, each thread adds one to one element of the array or subtracts one from one element of the array, then retests the sums for equality. At the moment of stopping all three streams, the sums of array elements must be the same.

package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func main() {
	var wg sync.WaitGroup
	wg.Add(3)
	var a [10]int
	var b [10]int
	var c [10]int
	for i := 0; i < 10; i++ {
		a[i] = rand.Intn(10)
		b[i] = rand.Intn(10)
		c[i] = rand.Intn(10)
	}
	go func() {
		defer wg.Done()
		for {
			sum1 := 0
			sum2 := 0
			sum3 := 0
			for i := 0; i < 10; i++ {
				sum1 += a[i]
				sum2 += b[i]
				sum3 += c[i]
			}
			if sum1 == sum2 && sum2 == sum3 {
				fmt.Println("Sum of a:", sum1)
				fmt.Println("Sum of b:", sum2)
				fmt.Println("Sum of c:", sum3)
				break
			}
			a[rand.Intn(10)] += rand.Intn(2)*2 - 1
			time.Sleep(100 * time.Millisecond)
		}
	}()
	go func() {
		defer wg.Done()
		for {
			sum1 := 0
			sum2 := 0
			sum3 := 0
			for i := 0; i < 10; i++ {
				sum1 += a[i]
				sum2 += b[i]
				sum3 += c[i]
			}
			if sum1 == sum2 && sum2 == sum3 {
				fmt.Println("Sum of a:", sum1)
				fmt.Println("Sum of b:", sum2)
				fmt.Println("Sum of c:", sum3)
				break
			}
			b[rand.Intn(10)] += rand.Intn(2)*2 - 1
			time.Sleep(100 * time.Millisecond)
		}
	}()
	go func() {
		defer wg.Done()
		for {
			sum1 := 0
			sum2 := 0
			sum3 := 0
			for i := 0; i < 10; i++ {
				sum1 += a[i]
				sum2 += b[i]
				sum3 += c[i]
			}
			if sum1 == sum2 && sum2 == sum3 {
				fmt.Println("Sum of a:", sum1)
				fmt.Println("Sum of b:", sum2)
				fmt.Println("Sum of c:", sum3)
				break
			}
			c[rand.Intn(10)] += rand.Intn(2)*2 - 1
			time.Sleep(100 * time.Millisecond)
		}
	}()
	wg.Wait()
}