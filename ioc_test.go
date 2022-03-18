package ioc_test

import (
	"testing"

	"github.com/IoC-Tools/common"
	"github.com/IoC-Tools/common/command"
	"github.com/IoC-Tools/ioc"
)

func TestRegister(t *testing.T) {
	var dep common.Dependency = func(args []common.Any) (common.Any, error) {
		var val = args[0].(int)
		return val + 1, nil
	}
	registerCommand, err := ioc.Resolve[command.ICommand](ioc.REGISTER, "key", dep)
	if err != nil {
		t.Error(err.Error())
	}
	_, err = ioc.Resolve[common.Any]("key", 1)
	if err == nil {
		t.Error("sholud be error: not found")
	}
	err = registerCommand.Invoke()
	if err != nil {
		t.Error(err.Error())
	}
	value, err := ioc.Resolve[int]("key", 1)
	if err != nil {
		t.Error(err.Error())
	}
	if value != 2 {
		t.Error("expected 2 got ", value)
	}
	c, _ := ioc.Resolve[command.ICommand](ioc.UNREGISTER, "key")
	c.Invoke()
}

func TestExecuteInNewScope(t *testing.T) {
	var dep common.Dependency = func(args []common.Any) (common.Any, error) {
		var val = args[0].(int)
		return val + 1, nil
	}
	var test = 0
	c, err := ioc.Resolve[command.ICommand](ioc.EXECUTE_IN_NEW_SCOPE, func() error {
		registerCommand, _ := ioc.Resolve[command.ICommand](ioc.REGISTER, "key", dep)
		registerCommand.Invoke()
		test, _ = ioc.Resolve[int]("key", test)
		return nil
	})
	if err != nil {
		t.Error(err.Error())
	}
	c.Invoke()
	if test != 1 {
		t.Error("expected 1 got ", test)
	}
	_, err = ioc.Resolve[common.Any]("key", 1)
	if err == nil {
		t.Error("sholud be error: not found")
	}
}

func TestConcurency(t *testing.T) {
	var dep common.Dependency = func(args []common.Any) (common.Any, error) {
		var val = args[0].(int)
		return val + 1, nil
	}

	registerCommand, _ := ioc.Resolve[command.ICommand](ioc.REGISTER, "key", dep)
	registerCommand.Invoke()

	go func() {
		var test = 0
		test, _ = ioc.Resolve[int]("key", test)

		if test != 1 {
			t.Error("expected 1 got ", test)
		}
	}()
}
