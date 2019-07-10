# -*- coding: UTF-8 -*-
import hashlib
import html
import json
import re
import time
import urllib.parse
import urllib.request
import uuid

import requests
from lxml import etree
from lxml import html as ht
from sqlalchemy import Column
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy.types import String

headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0'}
# 导入相应的模块
engine = create_engine(
    "mysql+mysqlconnector://csnight:night@123456@47.92.211.200:10024/wechat?")  # 创建数据库连接，max_overflow指定最大连接数
DBSession = sessionmaker(engine)  # 创建DBSession类型

BaseModel = declarative_base()  # 创建对象的基类


class Em(BaseModel):  # 定义User对象
    __tablename__ = 'emoji'  # 创建表，指定表名称
    # 指定表的结构
    id = Column(String, primary_key=True)
    ch_name = Column(String)
    en_name = Column(String)
    short_name = Column(String)
    category = Column(String)
    unicode_str = Column(String)
    unicode_char = Column(String)
    softbank_str = Column(String)
    sb_char = Column(String)
    google_str = Column(String)
    google_char = Column(String)


def baidu_translate(content, fro='en', to='zh'):
    m = hashlib.md5()
    salt = str(time.time()).split('.')[0]
    strs = str("20190526000302102" + content + salt + "dFU3K4Dplsy8Y00hLwyT").encode("utf-8")
    m.update(strs)
    '''实现百度翻译'''
    baidu_url = 'http://api.fanyi.baidu.com/api/trans/vip/translate'
    data = {}

    data['from'] = fro
    data['to'] = to
    data['q'] = content
    data['appid'] = '20190526000302102'
    data['sign'] = m.hexdigest()
    data['salt'] = salt
    data = urllib.parse.urlencode(data).encode('utf-8')
    baidu_re = urllib.request.Request(baidu_url, data)
    baidu_response = urllib.request.urlopen(baidu_re)
    baidu_html = baidu_response.read().decode('utf-8')
    target2 = json.loads(baidu_html)
    trans = target2['trans_result']
    ret = ''
    for i in range(len(trans)):
        ret += trans[i]['dst']
    return ret


def json_emoji_to_db():
    with open("emoji.json", "r", encoding="utf-8") as f:
        emojis = f.read()
        emoji_json = json.loads(emojis)
        for emoji in emoji_json:
            session = DBSession()  # 创建session对象
            uf = emoji["unified"].split("-")
            ud = ""
            sd = ""
            gd = ""
            if len(uf) > 1:
                for each in uf:
                    if len(each) == 4:
                        ud += '\\u%s' % each
                    elif len(each) == 5:
                        ud += '\\U000%s' % each
            else:
                if len(uf[0]) == 4:
                    ud = '\\u%s' % uf[0]
                elif len(uf[0]) == 5:
                    ud = '\\U000%s' % uf[0]
            sb = emoji["softbank"]
            if sb is not None:
                if len(sb) == 4:
                    sd = '\\u%s' % sb
                elif len(sb) == 5:
                    sd = '\\U000%s' % sb
            gb = emoji["google"]
            if gb is not None:
                if len(gb) == 4:
                    gd = '\\u%s' % gb
                elif len(gb) == 5:
                    gd = '\\U000%s' % gb

            em = Em(id=str(uuid.uuid4()), ch_name='',
                    en_name=emoji['name'],
                    short_name=emoji['short_name'],
                    category=emoji['category'], google_str=gb, google_char=gd.encode("utf-8").decode("unicode_escape"),
                    unicode_str=emoji['unified'], softbank_str=emoji['softbank'],
                    unicode_char=ud.encode("utf-8").decode("unicode_escape"),
                    sb_char=sd.encode("utf-8").decode("unicode_escape"))
            session.add(em)
            session.commit()
            session.close()


def en_to_zh():
    count = 0
    session = DBSession()
    ems = session.query(Em).all()
    session.close()
    for item in ems:
        session = DBSession()
        if item.en_name:
            session.query(Em).filter(Em.id == item.id).update(
                {"ch_name": baidu_translate(item.en_name)})
        else:
            if item.short_name.__contains__("_"):
                session.query(Em).filter(Em.id == item.id).update(
                    {"ch_name": baidu_translate(" ".join(item.short_name.split("_")))})
            elif item.short_name.__contains__("-"):
                session.query(Em).filter(Em.id == item.id).update(
                    {"ch_name": baidu_translate(" ".join(item.short_name.split("-")))})
            else:
                session.query(Em).filter(Em.id == item.id).update(
                    {"ch_name": baidu_translate(item.short_name)})
        count = count + 1
        session.commit()
        session.close()
        print(count)


def parse_qq_emoji():
    url = "http://www.wqchat.com/emoji.html"
    res = requests.request('GET', url=url)
    if res.status_code == 200:
        text = res.content.decode('utf-8')
        res.close()
        tree = etree.HTML(text)
        uls = tree.xpath('//ul/li')
        for each_li in uls:
            inner = ht.tostring(each_li)
            li = html.unescape(inner.decode())
            if li.__contains__("["):
                pat = r'\[\S*\]'
                pat_name = r'qqface\d*'
                pattern = re.compile(pat)
                pattern_name = re.compile(pat_name)
                ch = pattern.findall(li, 0, len(li))[0]
                st = ch.replace('[', '').replace(']', '')
                na = pattern_name.findall(li, 0, len(li))[0]
                session = DBSession()
                em = Em(id=str(uuid.uuid4()), ch_name=st,
                        en_name=baidu_translate(st, fro='zh', to='en'),
                        short_name=na,
                        category='QQ',
                        softbank_str=ch,
                        sb_char=ch)
                session.add(em)
                session.commit()
                session.close()


parse_qq_emoji()
