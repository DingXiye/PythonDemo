'''
Created on 2018年6月8日
文件操作
@author: dingye
'''
#打开文件
f=open("C:/Program Files/Python36/hello.txt")

#读取文件
#print(f.read())
#单行输出
for each_line in f:
    print(each_line)
    print(each_line[:3])#将字符串从第几个到第几个输出（0-3）

#获取当前文件指针位置
print(f.tell())

#seek(offset,from) offset表示偏移量，from(0代表从文件起始位置开始，1表示从当前位置开始，2表示从文件末尾开始)
#f.seek(2,1)#表示从当前位置偏移2个字节

#文件写入，a添加，w覆盖
f1=open("C:/Program Files/Python36/hello.txt","a")
f1.write("\n这是一段待写入")
f.close()


