package scopes

import "github.com/IoC-Tools/common"

type RegisterCommand struct {
	scope IScope
	key   string
	value common.Dependency
}

func (c *RegisterCommand) Invoke() error {
	return c.scope.Set(c.key, c.value)
}

type UnregisterCommand struct {
	scope IScope
	key   string
}

func (c *UnregisterCommand) Invoke() error {
	c.scope.Remove(c.key)
	return nil
}

type ScopeExecutionCommand struct {
	executionScope IScope
	fn             func() error
}

func (c *ScopeExecutionCommand) Invoke() error {
	var scope = GetCurrentScope()
	SetCurrentScope(c.executionScope)
	defer SetCurrentScope(scope)
	return c.fn()
}
