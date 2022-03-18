package ioc

import (
	"fmt"
)

type ResolveError struct {
	key   string
	cause string
}

func (e ResolveError) Error() string {
	return fmt.Sprintf("cannot resolve %s dependency: %s", e.key, e.cause)
}
