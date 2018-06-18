'''
Created on 2018年6月14日
Beautiful Soup
@author: dingye
'''
import urllib.request
from bs4 import BeautifulSoup
import re #实现正则表达式，正则表达式的通配符是"." re.search(r'Love.','I Loved you')
import urllib.parse

def main():
    keyword=input("请输入关键字:")
    keyword=urllib.parse.urlencode({"word":keyword})
    reponse=urllib.request.urlopen("http://baike.baidu.com/item/%s"%keyword)
    html=reponse.read()
    #第一个参数：需要提取数据的html文件  第二个参数：指定的解析器
    soup=BeautifulSoup(html,'html.parser')

    for each in soup.find_all(href=re.compile("view")):
        print(each.text)#含有view的所有a标签
        content =''.join([each.text])
        url2=''.join(["http://baike.baidu.com",each["href"]])
        print("url2:%s"%url2)
        reponse2=urllib.request.urlopen(url2)
        html2=reponse2.read()
        soup2=BeautifulSoup(html2,'html.parser')
        if soup2.h2:
            content=''.join([content,soup.h2.text])#将副标题一并打印出来
        print(content,"-->",''.join(url2))
    
if __name__=="__main__" :
    main()                        