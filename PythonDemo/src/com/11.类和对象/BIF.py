'''
Created on 2018年6月9日
相关的方法
@author: dingye
'''
#1.issubclass(class,classinfo):如果第一个类是第二个类的子类，则返回true
from inspect import getargs
class A:
    pass
class B(A):
    pass
print(issubclass(B,A))


#2.isinstance(object,classinfo):第一个参数是第二个参数的实例对象，返回true
b=B()
print(isinstance(b, B))
print(isinstance(b,A))

#3.hasattr(object,name):测试一个对象中是否有指定属性
#4.getattr(object,name[,defalut]):返回对象指定的属性值
class C():
    def __init__(self,x):
        self.x=x
c=C(1)
print(getattr(c, 'x'))

#5.setattr(object,name,value):给对象中指定属性设值
setattr(c, 'x', 3)

#6.delattr(object,name):删除对象中的指定属性

#7.property:通过属性设置属性

