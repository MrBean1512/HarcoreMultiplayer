######################################
#
#  CRINKLE CRUFT HARDCORE ADVENTURE
#
######################################
import wget
import requests
from bs4 import BeautifulSoup
import re
import json
import os
from shutil import copy
import glob

def main():
    server_path = get_server_path()
    plugin_path = get_plugin_path()
    while True:
        print("---------------------------------------------------------")
        print("             Crinkle Cruft Hardcore mode                 ")
        print("---------------------------------------------------------")
        print("  1. Install / Update BuildTools")
        print("  2. Update Hardcore Plugin")
        print("  3. Start Server")
        print("  4. Quit")
        while True:
            print(">> ", end='')
            answer = input()
            if answer == '1':
                install_update_build_tools(server_path)
                break
            elif answer == '2':
                update_plugin(plugin_path, server_path)
                break
            elif answer == '3':
                run_server(server_path)
                break
            elif answer == '4':
                return

def install_update_build_tools(server_path):
    current_ver = get_current_build_tools_version()
    latest_ver = get_latest_build_tools_version()
    if current_ver == 0:
        while True:
            print("You have not installed build tools yet. Would you like to install it now? (y/n)\n>> ", end='')
            answer = input().lower()
            if answer =='y':
                download_latest_build_tools(latest_ver, server_path)
                build_build_tools(server_path)
                break
            if answer == 'n':
                print("You cannot continue without BuildTools installed. Goodbye.")
                return
    else:
        update_build_tools(current_ver, latest_ver, server_path)

def run_server(server_path):
    print("Starting Server.")
    if os.path.isdir(server_path):
        generate_start_bat(server_path)
        os.chdir(server_path)
        os.system("start.bat")
        # os.chdir("..")

def get_server_data():
    jfile = open('server_info.json', 'r')
    data = json.load(jfile)
    jfile.close()
    return data    

def get_latest_build_tools_version():
    print("Getting latest BuildTools version.")
    page = requests.get('https://hub.spigotmc.org/jenkins/job/BuildTools/lastStableBuild/')
    soup = BeautifulSoup(page.content, 'html.parser')
    stitle = soup.title.text
    rex = re.search("^.*#([0-9]+).*$", stitle)
    return int(rex[1])

def get_current_build_tools_version():
    print("Getting current BuildTools Version.")
    data = get_server_data()
    return data["installed_version"]

def get_server_path():
    data = get_server_data()
    return data["server_path"]

def get_plugin_path():
    data = get_server_data()
    return data["plugin_path"]

def update_build_tools(current_ver, latest_ver, server_path):
    print("Checking for BuildTools Updates.")
    if latest_ver > current_ver:
        while True:
            print("There is a Spigot BuildTools update. Would you like to install it? (y/n)\n>> ", end='')
            answer = input().lower()
            if answer == 'y':
                download_latest_build_tools()
                while True:
                    print('\nWould you like to rebuild BuildTools.jar? (y/n)\n>> ', end='')
                    answer = input().lower()
                    if answer == 'y':
                        build_build_tools()
                        break
                    if answer == 'n':
                        break
                break
            if answer == 'n':
                break
    else:
        print("There are no available updates.")
    
def download_latest_build_tools(latest_ver, server_path):
    print("Downloading latest BuildTools.")
    data = get_server_data()
    if not os.path.isdir(server_path):
        os.mkdir(server_path)
    if os.path.isfile(server_path + "/BuildTools.jar"):
        os.remove(server_path + "/BuildTools.jar")
    url = 'https://hub.spigotmc.org/jenkins/job/BuildTools/lastStableBuild/artifact/target/BuildTools.jar'
    wget.download(url, server_path + '/BuildTools.jar')
    data["installed_version"] = latest_ver
    with open('server_info.json', 'w') as of:
        json.dump(data, of)

def update_plugin(plugin_path, server_path):
    print("Updating Plugin.")
    if os.path.isdir(server_path):
        for file in glob.glob(plugin_path + "/*.jar"):
            copy(file, server_path + "/plugins")

def build_build_tools(server_path):
    print("Building BuildTools.jar.")
    if os.path.isdir(server_path):
        os.chdir(server_path)
        os.system('java -jar BuildTools.jar')
        # os.chdir("..")

def generate_start_bat(server_path):
    print("Generating start.bat.")
    bat_text = r"""@echo off

java -jar spigot-1.16.5.jar -nogui"""

    if not os.path.isfile(server_path + "/start.bat"):
        with open(server_path + '/start.bat', 'w') as of:
            of.write(bat_text)


if __name__=="__main__":
    main()