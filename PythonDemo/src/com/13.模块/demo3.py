'''
Created on 2018年6月11日
搜索路径
@author: dingye
'''
import sys
print(sys.path)

#添加导入文件的路径
#加入要导入的文件在C:\\Python\\test\\m1中，第一步把模块所在位置添加到搜索路径中
sys.path.append("C:\\Python\\test\\m1")
print(sys.path)

#在每个文件夹中加入__init__.py文件后，文件夹就变成了包，Python将该目录当成一个包处理