'''
Created on 2018年6月14日
正则表达式爬取图片 
@author: dingye
'''
import urllib.request
import re
import os
def open_url(url):
    req=urllib.request.Request(url)
    req.add_header('User-Agent','Mozilla/5.0(Macintish;Intel Mac OS X 10_10_1) AppleWebKit537.36(KHTML,like Gecko)Chrome/39.0.2171.95 Safari/537.36')
    page =urllib.request.urlopen(req)
    html=page.read().decode("utf-8")
    return html

def get_img(html):
    p=r'<img class="BDE_Image".*?src="([^"]*\.jpg)".*?>'
    imglist=re.findall(p,html)
    try:
        os.mkdir("C:/NewPices")
    except FileExistsError:
        pass
    os.chdir("C:/NewPices")
    for each in imglist:
        filename=each.split("/")[-1]
        print(filename)
        urllib.request.urlretrieve(each, filename, None)#远程下载
if __name__=="__main__":
    url="http://tieba.baidu.com/p/3823765471"
    get_img(open_url(url))
        