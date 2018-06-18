'''
类
self相当于this
2018年6月9日
@author: dingye
'''
class Photo:
    def __init__(self,name):
        self.name=name
    def kick(self):
        print("我叫%s"%self.name)
p=Photo("土豆")
p.kick()

#在属性前加上"__"就变成私有属性 __name为私有属性
class Person:
    def __init__(self,name):
        self.__name=name
    def getname(self):
        return self.__name
p1=Person("dopa")
#p1.name
print(p1.getname())

#继承
class Parent:
    def h(self):
        print("这是父类方法")
class Child(Parent):
    pass
c=Child()
c.h()

#组合用来替代多重继承，多重继承会出现代码混乱，出现bug
class Base1:
    def __init__(self,name):
        self.name=name
class Base2:
    def __init__(self,name):
        self.name=name
class Pool:
    def __init__(self,x,y):
        self.base1=Base1(x)
        self.base2=Base2(y)
    def put(self):
        print("base1中%d,base2中%d"%(self.base1.name,self.base2.name))
pool =Pool(2,4)
pool.put()

#默认printb方法中会有一个参数即bb对象，这是Python的绑定机制
class BB:
    def printb():
        print("this bb")
b=BB()
#b.printb()

#with的实现
class TestWith:
    def __enter__(self):
        print("in __enter__")
        return "with"
    def __exit__(self, type, value, trace):
        print("type:", type)
        print("value:", value)
        print("trace:", trace)
        print("in __exit__")

def getWith():
    return TestWith()
with getWith() as f:
    print("sample:",f)
