'''
Created on 2018年5月20日
Python基础知识
@author: Administrator
'''
# 变量
from _ast import Num
teacher="xiao"
print(teacher)

# 字符串，将\n当做转义字符
string ='C:\now'
print(string)

# 转义字符的使用
string =r'C:\now'
print(string)

# 列表中添加元素
num=[1,2,3,"我"]
num.append(5)#列表添加一个元素
print(num)

num.extend([7,8])#列表中添加列表
print(num)

num.insert(3, 4)#在第四个位置插入一个元素
print(num)

name=["this","that","the"]#列表选择
print(name[2])

del name[1]#删除列表中一个元素
print(name)

name.remove("this")#删除列表中具体一个元素
print(name)

name.pop()#默认弹出最后一个元素

name[0:2]#选取两个索引之间的元素

list=["小猫",["小甲鱼","小鸡"],"小狗","小猪"]
print("小猪" in list)
print("小鸡" in list)

list=[1,1,1,2,3,4]
list.count(1) #获得1有几个  3

list.index(1)#获得1的索引

list.reverse()#将整个列表翻转

list.sort()#将列表排序

list2=list[:]#这是拷贝，list2不会随着list改变而改变
print("拷贝",list2)
list3=list#list3会随着list改变而改变

tuple=(1,2,3)#元组，和列表不同的地方是元组是不可改变的,逗号起决定作用
print(type(tuple))
print(8*(8))
print(8*(8,))

temp=("小鱼","小猪","小鸟")
temp=temp[:2]+("小狗",)+temp[2:]#跟新元组
print(temp)

del temp #删除元组，但是一般不会使用，因为Python回收机制会在这个元组不再使用时自动删除

temp=' '.join(['i','love','you'])#连接字符串
print(temp)

temp="{0} love {1}.{2}".format("i", "you","too")#格式化字符串
print(temp)

temp="{0}:{1:.2f}".format("圆周率",3.1415926)
print(temp)