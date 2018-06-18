'''
Created on 2018年6月11日
调用demo1中的方法
@author: dingye
'''
#这是第三种方法，也是推崇的方法
import demo1 as d1
print("32摄氏度=%.2f华氏度"%d1.cf(32))
print("99华氏度=%.2f摄氏度"%d1.f2(99))

