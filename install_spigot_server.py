import wget
import requests
from bs4 import BeautifulSoup
import re
import json
import os

print("Checking for BuildTools updates")
page = requests.get('https://hub.spigotmc.org/jenkins/job/BuildTools/lastStableBuild/')
soup = BeautifulSoup(page.content, 'html.parser')
stitle = soup.title.text
rex = re.search("^.*#([0-9]+).*$", stitle)
latest_ver = int(rex[1])

jfile = open('server_info.json')
data = json.load(jfile)
jfile.close()
current_ver = data["installed_version"]

if latest_ver > current_ver:
    while True:
        print("There is a Spigot BuildTools update. Would you like to install it? (y/n)\n>> ", end='')
        answer = input().lower()
        if answer == 'y':
            if not os.path.isdir("server"):
                os.mkdir("server")
            if os.path.isfile("server/BuildTools.jar"):
                os.remove("server/BuildTools.jar")
            url = 'https://hub.spigotmc.org/jenkins/job/BuildTools/lastStableBuild/artifact/target/BuildTools.jar'
            wget.download(url, 'server/BuildTools.jar')
            data["installed_version"] = latest_ver
            with open('server_info.json', 'w') as of:
                json.dump(data, of)
            while True:
                print('Would you like to (re)build BuildTools.jar? (y/n)\n>> ', end='')
                answer = input().lower()
                if answer == 'y':
                    os.chdir('server')
                    os.system('java -jar BuildTools.jar')
                    break
                if answer == 'n':
                    break
            break
        if answer == 'n':
            break


    


# https://hub.spigotmc.org/jenkins/job/BuildTools/lastStableBuild/artifact/target/BuildTools.jar