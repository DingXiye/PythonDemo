'''
序列部分方法的介绍
Created on 2018年5月21日

@author: Administrator
'''
#1.list
a=list("love") #list方法，将字符串的每个字符迭代存放到列表中
print(a)
b=list((1,1,2,3,4))#将每个元素迭代放入列表中
print(b)

#2.tuple 用于将一个可迭代的对象转换为元组
 
#3.str(obj) 用于将obj对象转化为字符串

#4.len(sub) 用于返回参数长度
tuple1="1","2","a"
print(len(tuple1))

#5.max(...) 用于输出序列中最大值
list=[1,4,9,2,77,5]
print(max(list))

#6.min(...) 用于输出最小值，注意这两个方法中参数类型必须一致

#7.sum(iterable[,start]) 用于返回和
tuple2=1,2,3,4
print(sum(tuple2))
print(sum(tuple2,10))

#8.sorted(iterable,key=None,reverse=False) 返回一个排列的序表
list1=[1,5,6,2,7,3]
print(sorted(list1))

#9.reversed(sequence) 用于返回逆向迭代序列的值

#10.enumerate(iterable) 生成二元组构成的一个迭代对象
str1="love"
for each in enumerate(str1):
    print(each)
    
#11.zip() 用于返回由各个 可迭代参数共同组成的元组
str1="love"
list=[1,3,5,7,8]
for each in zip(str1,list):
    print(each)
tuple1=2,4,6,8
for each in zip(str1,list,tuple1):
    print(each)