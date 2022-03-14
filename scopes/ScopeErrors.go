package scopes

import "fmt"

type ScopeErrorNotFound struct {
	key string
}

func (e ScopeErrorNotFound) Error() string {
	return fmt.Sprintf("dependency %s not found", e.key)
}

type ScopeErrorImmutable struct {
	key string
}

func (e ScopeErrorImmutable) Error() string {
	return fmt.Sprintf("dependency %s not registered. Immutable scope.", e.key)
}

type ScopeErrorUnset struct{}

func (e ScopeErrorUnset) Error() string {
	return "current scope is not set"
}
