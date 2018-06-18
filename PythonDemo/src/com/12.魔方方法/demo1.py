'''
Created on 2018年6月9日
构造和析构
@author: dingye
'''
#__new__()放回一个实例对象
from pip.utils.outdated import SELFCHECK_DATE_FMT
class Cap(str):
    def __new__(cls,string):
        string =string.upper()
        return str.__new__(cls,string)
a=Cap("I Love You")
print(a)

#__del__():析构函数，当垃圾回收机制回收这个对象时调用

#property使用属性访问属性
class C:
    def __init__(self,x):
        self.x=x
    def get(self):
        return self.x
    def set(self,y):
        self.x=y
    def dels(self):
        del self.x
    z=property(get,set,dels)
c=C(10)
print(c.z)
c.z=12
print(c.z)
del c.z
#print(c.z)


#迭代iter(),next()
string ='Love'
it=iter(string)
while True:
    try:
        each=next(it)
    except StopIteration:
        break
    print(each)
    
#魔方方法
class Iter():
    def __init__(self,n=20):
        self.a=0
        self.b=1
        self.n=n
    def __iter__(self):
        return self
    def __next__(self):
        self.a,self.b=self.b,self.a+self.b
        if self.a>self.n:
            raise StopIteration
        return self.a
iter=Iter()
for each1 in iter:
    print(each1)
   
#100内能被2整除不能被3整除
a=[i for i in range(100) if not(i%2) and i%3]
print(a)