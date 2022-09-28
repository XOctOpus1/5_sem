package main

import (
	"fmt"
)

type City struct {
	name string
}

type Route struct {
	from   *City
	to     *City
	price  int
	weight int
}

type Graph struct {
	cities []*City
	routes []*Route
}

func (g *Graph) addCity(name string) {
	g.cities = append(g.cities, &City{name: name})
}

func (g *Graph) addRoute(from, to *City, price, weight int) {
	g.routes = append(g.routes, &Route{from: from, to: to, price: price, weight: weight})
}

func (g *Graph) findCity(name string) *City {
	for _, city := range g.cities {
		if city.name == name {
			return city
		}
	}
	return nil
}

func (g *Graph) findRoute(from, to *City) *Route {
	for _, route := range g.routes {
		if route.from == from && route.to == to {
			return route
		}
	}
	return nil
}

func (g *Graph) findRoutes(from, to *City) []*Route {
	var routes []*Route
	for _, route := range g.routes {
		if route.from == from && route.to == to {
			routes = append(routes, route)
		}
	}
	return routes
}

func (g *Graph) findRoutesByCity(city *City) []*Route {
	var routes []*Route
	for _, route := range g.routes {
		if route.from == city || route.to == city {
			routes = append(routes, route)
		}
	}
	return routes
}

func (g *Graph) removeCity(city *City) {
	for i, c := range g.cities {
		if c == city {
			g.cities = append(g.cities[:i], g.cities[i+1:]...)
			break
		}
	}
}

func (g *Graph) removeRoute(route *Route) {
	for i, r := range g.routes {
		if r == route {
			g.routes = append(g.routes[:i], g.routes[i+1:]...)
			break
		}
	}
}

func (g *Graph) changePrice(route *Route, price int) {
	route.price = price
}

func (g *Graph) changeWeight(route *Route, weight int) {
	route.weight = weight
}

func (g *Graph) print() {
	fmt.Println("Cities:")
	for _, city := range g.cities {
		fmt.Println(city.name)
	}
	fmt.Println("Routes:")
	for _, route := range g.routes {
		fmt.Printf("%s -> %s: %d, %d", route.from.name, route.to.name, route.price, route.weight)
	}
}

func (g *Graph) findPath(from, to *City) (int, []*Route) {
	var (
		visited = make(map[*City]bool)
		queue   = make([]*City, 0)
		paths   = make(map[*City][]*Route)
	)
	queue = append(queue, from)
	visited[from] = true
	for len(queue) > 0 {
		city := queue[0]
		queue = queue[1:]
		for _, route := range g.findRoutesByCity(city) {
			var next *City
			if route.from == city {
				next = route.to
			} else {
				next = route.from
			}
			if !visited[next] {
				visited[next] = true
				queue = append(queue, next)
				paths[next] = append(paths[next], route)
			}
		}
	}
	if !visited[to] {
		return 0, nil
	}
	var (
		price  = 0
		routes = make([]*Route, 0)
	)
	for city := to; city != from; city = routes[len(routes)-1].from {
		routes = append(routes, paths[city][0])
		price += paths[city][0].price
	}
	return price, routes
}

func (g *Graph) findPathByWeight(from, to *City) (int, []*Route) {
	var (
		visited = make(map[*City]bool)
		queue   = make([]*City, 0)
		paths   = make(map[*City][]*Route)
	)
	queue = append(queue, from)
	visited[from] = true
	for len(queue) > 0 {
		city := queue[0]
		queue = queue[1:]
		for _, route := range g.findRoutesByCity(city) {
			var next *City
			if route.from == city {
				next = route.to
			} else {
				next = route.from
			}
			if !visited[next] {
				visited[next] = true
				queue = append(queue, next)
				paths[next] = append(paths[next], route)
			}
		}
	}
	if !visited[to] {
		return 0, nil
	}
	var (
		weight = 0
		routes = make([]*Route, 0)
	)
	for city := to; city != from; city = routes[len(routes)-1].from {
		routes = append(routes, paths[city][0])
		weight += paths[city][0].weight
	}
	return weight, routes
}

func main() {
	var (
		g = &Graph{}
	)
	g.addCity("A")
	g.addCity("B")
	g.addCity("C")
	g.addCity("D")

	g.addRoute(g.findCity("A"), g.findCity("B"), 10, 10)
	g.addRoute(g.findCity("A"), g.findCity("C"), 20, 20)
	g.addRoute(g.findCity("A"), g.findCity("D"), 30, 30)
	g.addRoute(g.findCity("B"), g.findCity("C"), 40, 40)
	g.addRoute(g.findCity("B"), g.findCity("D"), 50, 50)
	g.addRoute(g.findCity("C"), g.findCity("D"), 60, 60)

	g.print()

	fmt.Println("Find path by price:")
	price, routes := g.findPath(g.findCity("A"), g.findCity("D"))
	fmt.Printf("Price: %d\n", price)
	for _, route := range routes {
		fmt.Printf("%s -> %s: %d, %d", route.from.name, route.to.name, route.price, route.weight)
	}

	fmt.Println("Find path by weight:")
	weight, routes := g.findPathByWeight(g.findCity("A"), g.findCity("D"))
	fmt.Printf("Weight: %d\n", weight)
	for _, route := range routes {
		fmt.Printf("%s -> %s: %d, %d", route.from.name, route.to.name, route.price, route.weight)
	}
}
