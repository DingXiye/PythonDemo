'''
Created on 2018年6月13日
隐藏，让链接看起来是普通浏览器的正常点击
延迟提交数据，使用time模块
@author: dingye
'''
#在request对象生成后，用add_header()追加进去
import urllib.request
import urllib.parse
import json
import time 

content=input("请输入翻译内容:")
url="http://fanyi.youdao.com/translate_o?smartresult=dict&smartresult=rule&smartresult=ugc&sessionFrom=null"
data={}
data['type']='AUTO'
data['i']=content
data['doctype']='json'
data['xmlVersion']='2.1'
data['keyfrom']='fanyi.web'
data['ue']='UTF-8'
data['typoResult']='true'
data=urllib.parse.urlencode(data).encode('utf-8')
req=urllib.request.Request(url,data)
req.add_header('Referer', "http://fanyi.youdao.com")
req.add_header('User_Agent', 'Mozilla/5.0(Macintosh;Intel Mac OS X 10_10_1) AppleWebKit/537.36(KHTML.like Gecko) Chrome/39.0.2171.95 Safari/537.36X-Requested-With:XMLHttpRequest')
response=urllib.request.urlopen(req)
html=response.read().decode('utf-8')
target=json.loads(html)
print("翻译结果：%s"%(target['translateResult'][0][0]['tgt']))
time.sleep(5)