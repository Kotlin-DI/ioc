package ioc

import (
	"github.com/IoC-Tools/common"
	"github.com/IoC-Tools/ioc/scopes"
)

func Resolve(key string, args ...common.Any) (common.Any, error) {
	var (
		dependency common.Dependency
		err        error
	)
	var scope = scopes.GetCurrentScope()
	dependency, err = scope.Get(key)
	if err != nil {
		return nil, err
	} else {
		return dependency(args)
	}
}
