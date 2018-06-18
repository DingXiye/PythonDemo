'''
Created on 2018年5月20日
第一个简单的python例子
@author: dingye
'''
import random

screct = random.randint(1, 10)
temp = input("不妨猜一下我心中所想的数字:")
guss = int(temp);
while guss != screct:
    temp = input("哎呀，猜错了，请重新输入：")
    guss = int(temp)
    if guss == screct:
        print("你是我的蛔虫吗")
        print("猜对也没有奖励")
    else:
        if guss>screct:
             print("猜大了")
        else:
            print("猜小了")
print("游戏结束")
