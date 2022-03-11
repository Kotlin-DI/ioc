package ioc

import "github.com/IoC-Tools/common"

type IScope interface {
	common.Getter
	common.Setter
}
