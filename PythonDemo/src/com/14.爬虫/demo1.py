'''
Created on 2018年6月13日
爬虫入门知识
@author: dingye
'''
import urllib.request
reponse=urllib.request.urlopen("http://www.fishc.com").read().decode("utf-8")
print(reponse)#b表示输出的为bytes对象