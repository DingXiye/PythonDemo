'''
Created on 2018年5月30日
字典和集合的应用
字典就是hash，是一种映射类型
@author: dingye
'''
from test.test_logging import test_compute_rollover
dir={"李宁":"一切皆有可能","耐克":"just do it"}
print(dir['李宁'])

#第一种表示方式
dict1=dict((('f',6),('i',105)))
print(dict1)

#第二种表示方式，键不用加引号
dict2=dict(f=70,i=105)
print(dict2)

#直接给键值对赋值
dict2['x']=100
print(dict2)

#共有5种字典创建方式
a=dict((('a',20),('b',30)))
b={'a':20,'b':30}
c=dict(zip(['a','b'],[20,30]))
d=dict([('a',20),('b',30)])
e=dict({'a':20,'b':30})
print(a==b==c==d==e)

#1.fromkeys()创建返回一个新的字典
dict3={}
dict3=dict3.fromkeys((1,2,3),"you")
print(dict3)

#2.key()输出所有键； value()输出所有值；  item()键值对
dict4={}
dict4=dict4.fromkeys(range(32), "zan")
dict4.keys()
dict4.values()
dict4.items()

#3.get()返回键对应的值
get=dict4.get(31)
print(get)

#4.copy()复制字典

#5.pop()瘫吹指定键对应的值；  popitem()弹出指定项
print(dict1.popitem())
print(dict1.pop('f'))

#update() 跟新字典
pet={"dy":"sjw","xiye":"dopa"}
pet.update(dy="dopa")
print(pet)

#创建集合
set1={"i","love","you"}
set2=set(["i","love","you"])

#去除重复值
list1=[1,2,3,1,24,3]
list1=list(set(list1))
print(list1)


