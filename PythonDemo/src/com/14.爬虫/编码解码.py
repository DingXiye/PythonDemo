'''
Created on 2018年6月13日
编码解码
@author: dingye
'''
#编码：将unicode编码转换成其他编码的字符串
str="你好"
print(type(str))
str=str.encode('utf_8')
print(str)#b'\xe4\xbd\xa0\xe5\xa5\xbd'
#解码：将其他编码的字符串转换成unicode编码
str=str.decode('utf_8')
print(str)#你好