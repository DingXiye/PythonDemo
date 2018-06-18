'''
函数知识点
2018年5月21日
@author: dingye
'''
#1.创建方法并且调用
from test.test_tools.test_unparse import nonlocal_ex
def myfunctions():
    print("第一个函数方法")
myfunctions()

#2.形参实参
def myf(name,classname):
    print(name+"是"+classname+"班的")
myf("兮夜","软卓")

#3.收集参数
def test(*params,extra):
    print("有%d个参数"%len(params))
    print("位置参数:",extra)
test('l','o','v','e',extra=8)

def test1(*params1):
    print("有%d个参数"%len(params1))
    print("第二个参数是:",params1[1])
a=[1,2,3,4,5,6]
test1(*a)# *既可解包也可打包，在这用作解包，如果不加*会出错

# Python 只有函数，没有过程;当不写return时，默认返回None
def hello():
    print("hello")
print(hello())

#测试return
def test2():
    return [1,"love",2.1]
print(test2())

'''
#在函数中修改全局变量时，函数中会创建一个与全局变量名一样的局部变量
def test3(price):
    price=80.0
    print("函数中的全局变量值：",price)#80
price =float(input("输入全局变量："))
test3(price)
print("全局变量值：",price)#输入值
'''

#global关键字,将局部变量转化为全局变量
count=5
def test4():
    global count
    count =10
    print(count)
test4()
print(count)

#闭包定义：如果在一个内部函数里，对外部作用域的变量进行引用，那么内部函数就被认为是闭包
def fun1(x):
    def fun2(y):
        return x*y
    return fun2
print(fun1(2)(6))

#在函数f2中认为x是局部变量，所以x*=x中右边找不到局部变量x的值
def f1():
    x=3
    def f2():
        nonlocal x #可以使用nonlocal关键字
        x*=x 
        return x
    return f2
print(f1()())

#lambda创建匿名函数,冒号左边是参数，右边是返回值
g=lambda x,y:x+y
print(g(5,7))

#filter,过滤器，有两个参数，第一个参数是函数或者none，第二个参数为可迭代序列
def odd(x):  #过滤奇数
    return x%2
temp=filter(odd,range(10)) 
print(list(temp))
#等同于 list(filter(lambda x:x%2,range(10)))

#map与filter参数一样
print(list(map(lambda x:x*2,range(10))))

'''
#使用递归计算阶乘
def factorial(x):
    if x==1:
        return 1
    else:
        return x*factorial(x-1)
number=int(input("输入一个整数："))
result=factorial(number)
print("阶乘结果:",result)
'''

#递归实现汉诺塔
def hanoi(n,x,y,z):
    if n==1:
        print(x,'-->',z)
    else:
        hanoi(n-1,x,z,y)
        print(x,"-->",z)
        hanoi(n-1, y,x,z)
n=int(input("输入层数："))
hanoi(n, 'X', 'Y','Z')