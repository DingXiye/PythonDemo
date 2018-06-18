'''
Created on 2018年6月13日
使用代理:在爬虫中不停地使用同一个ip可能会被禁止，所以通过代理ip可以不停地访问
也可以使用iplist，每次随机使用一个ip地址访问
@author: dingye
'''
import urllib.request
import random

url='https://study.163.com/'

#iplist=input("添加多个IP地址(使用；隔开)：").split(sep=";")
iplist = [
    {"http" : "61.135.217.7:80"},
    {"http" : "111.155.116.245:8123"},
    {"http" : "122.114.31.177:808"},
]
while True:
    ip=random.choice(iplist)
    #1.参数是一个字典，字典的键是代理的类型，值是代理的IP地址和端口号
    proxy_support=urllib.request.ProxyHandler({'http':ip})
    #2.使用builder创建一个属于自己的私人订制的opener
    opener=urllib.request.build_opener(proxy_support)
    #3.将定制好的opener安装到系统中
    urllib.request.install_opener(opener)
    try:
        print("正在尝试使用%s连接"%ip)
        reponse=urllib.request.urlopen(url)
    except urllib.error.URLError:
        print("访问出错")
    else:
        print("访问成功")
    if input("请问是否继续?(Y/N)")=='N':
        break
#     html=reponse.read().decode('utf-8')
#     print(html)
