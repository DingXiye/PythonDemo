'''
Created on 2018年6月11日
方法一
@author: dingye
'''
def cf(cel):
    fah=cel*1.8+32
    return fah
def f2(fah):
    cel=(fah-32)/1.8
    return cel
def test():
    print("这是一个测试方法")
#只有单独运行demo1是才会执行test方法，在demo2中引用demo1时将不会执行
if __name__=='__main__':
    test()