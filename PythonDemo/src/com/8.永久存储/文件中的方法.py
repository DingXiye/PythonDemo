'''
Created on 2018年6月8日
文件常用方法
@author: dingye
'''
#1.getcwd()获得工作目录
import os
print(os.getcwd())

#2.chdir() 改变工作目录

#3.listdir() 获取所有子目录及文件
print(os.listdir())

#4.mkdir() 创建文件夹
#5.makedirs() 创建多层目录
#os.makedirs(r".\a\b\c")

#6.remove("指定文件") rmdir(目录) removedirs(r"\a\b\c")

#7.rename(old,new)

#8.system(command)：使用系统提供的工具
#os.system("calc")

#9.walk(top) 遍历top参数指定路径下的所有子目录
# for i in os.walk("C:"):
#     print(i)


#以下是path()下的方法
#10.basename():获取文件名  dirname()：获取路径名
print(os.path.basename(os.getcwd()))
print(os.path.dirname(os.getcwd()))

#11.join() 将文件名与路径组合成一个完整路径

#12.split() 分割路径

#13.getsize():获取文件尺寸（字节）

#14.getatime():最近访问时间；  getctime():创建时间；   getmtime():修改时间


#保存任何类型的数据
import pickle

my_list=[12,"hello",['list']]
pick_file=open("pick.pkl",'wb')
pickle.dump(my_list,pick_file)#保存
pick_file.close()

# pickle.load(pick_file)#打开文件
