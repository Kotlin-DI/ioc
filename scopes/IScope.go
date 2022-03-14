package scopes

import (
	"github.com/IoC-Tools/common"
)

type IScope interface {
	Set(key string, value common.Dependency) error
	Get(key string) (common.Dependency, error)
}
