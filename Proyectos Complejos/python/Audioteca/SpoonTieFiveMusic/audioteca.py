import xml.etree.ElementTree as ET
from tkinter import *
from tkinter import ttk
from tkinter import filedialog
import datetime
import os
import shutil

#needed variables
page=0
pageSong=0
pageArtists=0
visibleDisks=[0,1,2,3,4,5]
showed=[0, 1, 2, 3]
songLink=""

#xml file
filename="MusicaXML/audioteca.xml"

#main window configuration
wndw=Tk()
wndw['bg']="black"
wndw.title("SpoonTieFive")
app_icon=PhotoImage(file="src/proyect_images/icono.png")
wndw.iconphoto(True,app_icon)
wndw.geometry("870x1000")

#Images
add_artist_icon=PhotoImage(file = "src/proyect_images/nuevoartista2.png")
no_sound_icon=PhotoImage(file = "src/proyect_images/sinsonido.png")
trap_icon=PhotoImage(file = "src/proyect_images/trap.png")
other_icon=PhotoImage(file = "src/proyect_images/otros.png")
no_sound_icon=PhotoImage(file = "src/proyect_images/sinsonido.png")
icon_new = PhotoImage(file = "src/proyect_images/anhadirverde2.png")
other_mini_icon=PhotoImage(file = "src/proyect_images/otrospeque.png")
icon_change_green=PhotoImage(file = "src/proyect_images/cambiarverde2.png")
icon_cd=PhotoImage(file = "src/proyect_images/disco.png")
arrow_icon=PhotoImage(file = "src/proyect_images/flecha.png")
arrowLeft_icon=PhotoImage(file = "src/proyect_images/flechaizq.png")
delete_icon=PhotoImage(file = "src/proyect_images/borrar.png")
electronic_icon=PhotoImage(file = "src/proyect_images/e.png")
electronic_black=PhotoImage(file = "src/proyect_images/ebn.png")
rock_icon=PhotoImage(file = "src/proyect_images/r.png")
rock_black=PhotoImage(file = "src/proyect_images/rbn.png")
jazz_icon=PhotoImage(file = "src/proyect_images/jazz.png")
jazz_black=PhotoImage(file = "src/proyect_images/jazzbn.png")
pop_icon=PhotoImage(file = "src/proyect_images/pop.png")
pop_black=PhotoImage(file = "src/proyect_images/popbn.png")
classic_icon=PhotoImage(file = "src/proyect_images/clasica.png")
classic_black=PhotoImage(file = "src/proyect_images/clasicabn.png")
reggaeton_icon=PhotoImage(file = "src/proyect_images/reggaeton.png")
reggaeton_black=PhotoImage(file = "src/proyect_images/reggaetonbn.png")
trap_black=PhotoImage(file = "src/proyect_images/trapbn.png")
other_black=PhotoImage(file = "src/proyect_images/otrosbn.png")
edit_icon=PhotoImage(file = "src/proyect_images/editarpeque.png")
more_artists_icon=PhotoImage(file = "src/proyect_images/tresp.png")
more_songs_icon=PhotoImage(file = "src/proyect_images/mas.png")
pause_icon=PhotoImage(file = "src/proyect_images/pause.png")
play_icon=PhotoImage(file = "src/proyect_images/play.png")
find_song_icon=PhotoImage(file = "src/proyect_images/lupapeque.png")
classic_mini_icon=PhotoImage(file = "src/proyect_images/clasicapeque.png")
electronic_mini_icon=PhotoImage(file = "src/proyect_images/epeque.png")
jazz_mini_icon=PhotoImage(file = "src/proyect_images/jazzpeque.png")
pop_mini_icon=PhotoImage(file = "src/proyect_images/poppeque.png")
rock_mini_icon=PhotoImage(file = "src/proyect_images/rpeque.png")
reggaeton_mini_icon=PhotoImage(file = "src/proyect_images/reggaetonpeque.png")
trap_mini_icon=PhotoImage(file = "src/proyect_images/trappeque.png")
delete_45_icon=PhotoImage(file = "src/proyect_images/borrar45.png")

#Basic Labels and Buttons
l1newdisk=Button(wndw,
        image=icon_new,
        background="black",
        width=60,
        height=60)
l1newdisk.grid(row=0, column=0)

l2=Label(wndw,
        text="Spoon Tie Five",
        font="Verdana 40",
        background="black",
        fg="#00bf36").grid(row=0, column=1, columnspan=2, rowspan=2, padx=100) 

l3xml=Button(wndw,
        image=icon_change_green,
        background="black",
        width=60,
        height=60)
l3xml.grid(row=0, column=3)

l4file=Label(wndw,
        text="fichero: audioteca.xml",
        font="Verdana 15",
        background="black",
        fg="#00bf36")
l4file.grid(row=2, column=1, columnspan=2, sticky="s")        

l4_2=Label(
        wndw,
        text="a",
        font="Verdana 15",
        background="black",
        fg="black",
        height=3
).grid(row=2, column=0 )

l5=Label(wndw,
        text="añadir disco",
        font="Verdana 15",
        background="black",
        fg="#949494").grid(row=1, column=0)         

l6=Label(wndw,
        text="cambiar xml",
        font="Verdana 15",
        background="black",
        fg="#949494").grid(row=1, column=3)               

l7=Label(
        wndw,
        text="a",
        fg="black",
        background="black",
        height=4
).grid(row=3, column=0, columnspan=4)

#functions
def limitname(disknameaux):#returns a cuted string if it is too long 
        diskname=""
        for i in range(9):
                if i<len(disknameaux):diskname+=disknameaux[i]
        return diskname        

#root for xml
tree = ET.parse(filename)
root=tree.getroot()

#DISKS INTERFACE
numDisks=len(root)
list_d=[]
list_n=[]
list_p=[]
nrow=4
ncolumn=1
for i in range(6):
        d=Button(
                wndw,
                image=icon_cd,
                width=150,
                height=150
        )

        n=Label(
                wndw,
                font="Verdana 15"
        )

        p=Button(
                wndw,
                image=delete_icon,
                width=20,
                height=20,
                background="black"
        )

        list_d.append(d)
        list_n.append(n)
        list_p.append(p)        
        if(numDisks>=i+1):
                if(i%2==0):
                        list_d[i].grid(row=nrow, column=ncolumn, sticky="e", padx=15, pady=15)
                        list_p[i].grid(row=nrow, column=ncolumn, sticky="ne", pady=15, padx=15)
                else:
                        list_d[i].grid(row=nrow, column=ncolumn, sticky="w", padx=15, pady=15)
                        list_p[i].grid(row=nrow, column=ncolumn, sticky="nw",padx=15, pady=15)
                        
                name=limitname(root[i].attrib["nombre"])
                list_n[i].configure(text=name)
                list_n[i].grid(row=nrow, column=ncolumn, sticky="s")

                if(i%2==1):
                        ncolumn=1
                        nrow+=1
                else:
                        ncolumn=2

#creating right and left arrows
arrowR=Button(
        wndw,
        image=arrow_icon,
        width=30,
        height=30,
        background="white"
)

arrowL=Button(
        wndw,
        image=arrowLeft_icon,
        width=30,
        height=30,
        background="white"
)

#more functions
def changeDisksView():
        global root, page, numDisks, list_d, list_n, list_p, visibleDisks
        position=page*6
        visibleDisks=[page, page+1, page+2, page+3, page+4, page+5]

        nrow=4
        ncolumn=1
        for i in range(6):
                if(numDisks==0):
                        list_p[i].grid_forget()
                        list_n[i].grid_forget()
                        list_d[i].grid_forget()

                elif(position+1<=numDisks):
                        if(i%2==0):
                                list_d[i].grid(row=nrow, column=ncolumn, sticky="e", padx=15, pady=15)
                                list_p[i].grid(row=nrow, column=ncolumn, sticky="ne", pady=15, padx=15)
                        else:
                                list_d[i].grid(row=nrow, column=ncolumn, sticky="w", padx=15, pady=15)
                                list_p[i].grid(row=nrow, column=ncolumn, sticky="nw",padx=15, pady=15)       
                        list_n[i].grid(row=nrow, column=ncolumn, sticky="s")

                        list_n[i].config(
                                text=limitname(root[position].attrib["nombre"])
                        )
                else:
                        list_p[i].grid_forget()
                        list_n[i].grid_forget()
                        list_d[i].grid_forget()

                if(i%2==1):
                        ncolumn=1
                        nrow+=1
                else:
                        ncolumn=2        
                position+=1                


def showDisksRight():
        global arrowR
        global arrowL
        global page
        page+=1
        lastDiskOfGroup=(page*6)+5
        
        if(numDisks<=lastDiskOfGroup+1):
                arrowR.grid_forget()
        arrowL.grid(row=7, column=1)
        changeDisksView()

def showDisksLeft():
        global arrowR
        global arrowL
        global page
        page-=1
        if(page==0):
                arrowL.grid_forget()
        arrowR.grid(row=7, column=2)
        changeDisksView()

#arrows configuration
if(numDisks>6):
        arrowR.config(command=showDisksRight)
        arrowR.grid(row=7, column=2)
        arrowL.config(command=showDisksLeft)
        spaceaux=Label(
                wndw,
                background="black",
                text=" ",
                fg="white",
                height=10
        ).grid(row=7, column=0)

#ask if the user wants to open an existing xml or create a new one
def askChangeCreate():
        askwndw = Toplevel(wndw)
        askwndw.title("¿Que quiere hacer?")
        askwndw.geometry("450x250")
        askwndw['bg']='black'

        Label(
                askwndw,
                background="black",
                fg="#00bf36",
                font="Verdana 30",
                text="¿Qué deseas hacer?"
        ).grid(row=0, column=0, columnspan=2)

        change=Button(
                askwndw,
                background="#00bf36",
                font="Verdana 15",
                text="Abrir xml"
        )
        change.grid(row=1, column=0, pady=30)

        create=Button(
                askwndw,
                background="#00bf36",
                font="Verdana 15",
                text="Crear xml"
        )
        create.grid(row=1, column=1)

        def changeOption():
                askwndw.destroy()
                changeXML()

        change.configure(command=changeOption)

        def createOption():
                askwndw.destroy()
                createNewXML()

        create.configure(command=createOption)
        

def createNewXML():
        createXMLwndw = Toplevel(wndw)
        createXMLwndw.title("Crear audioteca")
        createXMLwndw.geometry("500x250")
        createXMLwndw['bg']='black'

        Label(
                createXMLwndw,
                text="Escribe el nombre de tu audioteca",
                background="black",
                fg="#00bf36",
                font="Verdana 20"
        ).grid(row=0, column=0)

        inputname = Text(
                createXMLwndw,
                height = 1,
                width = 15,
                font="Verdana 30"
        )
        inputname.grid(row=1, column=0, pady=30)

        bAudioLibrary = Button(
                createXMLwndw,
                font="Verdana 15",
                background="#00bf36",
                text="Crear"
        )
        bAudioLibrary.grid(row=2, column=0)

        def createXMLInFolder():
                global filename, root, tree, page, numDisks
                fileToCreate="./MusicaXML/"+ inputname.get("1.0", "end-1c")
                fileToCreateAux=fileToCreate+".xml"

                if(os.path.isfile(fileToCreateAux)==True):
                        count=1
                        stay=True
                        while (stay==True):
                                fileToCreateAux=fileToCreate+str(count)+".xml"
                                if(os.path.isfile(fileToCreateAux)==False):
                                        stay=False
                                count+=1        
                        

                with open(fileToCreateAux, 'w') as f:
                        f.write("<audioteca>\n</audioteca>")

                createXMLwndw.destroy()

                filename=fileToCreateAux
                tree = ET.parse(filename)
                root=tree.getroot()
                f=open(filename, 'r')
                l4file.configure(text="fichero: "+os.path.basename(f.name))
                numDisks=len(root)
                page=0
                if(numDisks>6):
                        arrowR.grid(row=7, column=2)
                else:
                        arrowR.grid_forget()
                changeDisksView()


        bAudioLibrary.configure(command=lambda:createXMLInFolder())        


#function for updating disks
def changeXML():
        global root, tree, filename, numDisks, page, arrowR, l4file
        lastfilename=filename
        first_page()
        filename=filedialog.askopenfilename(title = "Select file",filetypes = (("XML Files","*.xml"),))
        if(len(filename)<4):
                filename=lastfilename
        tree = ET.parse(filename)
        root=tree.getroot()
        f=open(filename, 'r')
        l4file.configure(text="fichero: "+os.path.basename(f.name))
        numDisks=len(root)
        page=0
        if(numDisks>6):
                arrowR.grid(row=7, column=2)
        else:
                arrowR.grid_forget()
        changeDisksView()                

#reads againg the xml file
def readXML():
        global tree, root, numDisks
        tree = ET.parse(filename)
        root=tree.getroot()
        numDisks=len(root)

#returns to the first page to avoid bugs
def first_page():
        global arrowL, page, numDisks, arrowR
        arrowL.grid_forget()
        page=0
        if numDisks>6:
                arrowR.grid(row=7, column=2)
        else:
                arrowR.grid_forget()        
        changeDisksView()

#adding the command to the xml button
l3xml.configure(
        command=askChangeCreate
)

#obtain current year for using as default year on the disks
currentDateTime = datetime.datetime.now()
date = currentDateTime.date()
year = date.strftime("%Y")

"""
Disco Creator interface and code:
***********************************************************************************************************************************************
"""
#creates a new window asking the information about the new album
def windowNewAlbum():

        #images
        global electronic_black, rock_black, jazz_black, pop_black, classic_black, reggaeton_black, trap_black, other_black, add_artist_icon

        #add album main interface
        newAlbumwndw = Toplevel(wndw)
        newAlbumwndw.title("Crea aquí tu nuevo disco!!!")
        newAlbumwndw.geometry("840x900")
        newAlbumwndw['bg']='black'
        
        #labels and texts
        Label(
                newAlbumwndw,
                text="A",
                font="Verdana 100",
                background="black",
                fg="black"
        ).grid(row=0, column=0)

        Label(
                newAlbumwndw,
                text="Crea tu nuevo disco",
                font="Verdana 40",
                background="black",
                fg="#00bf36"  
        ).grid(row=0, column=1, columnspan=4)
        
        Label(
                newAlbumwndw,
                text="Nombre :",
                font="Verdana 20",
                background="black",
                fg="#00bf36"
        ).grid(row=1, column=1)

        inputname = Text(newAlbumwndw,
                        height = 1,
                        width = 15,
                        font="Verdana 30"
                )
  
        inputname.grid(row=1, column=2, columnspan=3, pady=10, padx=10, sticky="w")

        Label(
                newAlbumwndw,
                text="Artista :",
                font="Verdana 20",
                background="black",
                fg="#00bf36"
        ).grid(row=2, column=1)

        Label(
                newAlbumwndw,
                text="Artistas :",
                font="Verdana 20",
                background="black",
                fg="#00bf36"
        ).grid(row=3, column=1, pady=15)

        artists=Label(
                newAlbumwndw,
                font="Verdana 20",
                background="black",
                fg="#00bf36"
        )

        artists.grid(row=3, column=2, columnspan=3, sticky="w")

        inputartist = Text(newAlbumwndw,
                        height = 1,
                        width = 10,
                        font="Verdana 30"
                        )
  
        inputartist.grid(row=2, column=2, columnspan=2, pady=10, padx=10, sticky="w")

        add_artist=Button(
                newAlbumwndw,
                width=30,
                height=30,
                image=add_artist_icon,
                background="black"
        )

        add_artist.grid(row=2, column=4)

        Label(
                newAlbumwndw,
                text="Año :",
                font="Verdana 20",
                background="black",
                fg="#00bf36"
        ).grid(row=4, column=1, pady=15)

        inputyear = Text(newAlbumwndw,
                        height = 1,
                        width = 10,
                        font="Verdana 30"
                        )
        inputyear.grid(row=4, column=2, columnspan=2, sticky="w")                

        Label(
                newAlbumwndw,
                text="Géneros :",
                font="Verdana 20",
                background="black",
                fg="#00bf36"
        ).grid(row=5, column=1, pady=15)

        #Ok adn Cancel buttons
        bok=Button(
                newAlbumwndw,
                text="Crear",
                font="Verdana 20",
                background="#00bf36"
        )
        bok.grid(row=8, column=1)

        Button(
                newAlbumwndw,
                text="Cancelar",
                font="Verdana 20",
                background="grey",
                command= lambda: newAlbumwndw.destroy()
        ).grid(row=8, column=4, pady=15)


        #genres buttons and names
        bElectronic=Button(
                newAlbumwndw,
                background="black",
                width=100,
                height=100,
                image=electronic_black
        )
        bElectronic.grid(column=1, row=6, pady=30, padx=15)

        Label(
                newAlbumwndw,
                background="black",
                text="Electrónica",
                fg="#00bf36",
                font="Verdana 10"
        ).grid(column=1, row=6, sticky="s", pady=20)

        bRock=Button(
                newAlbumwndw,
                background="black",
                width=100,
                height=100,
                image=rock_black
        )
        bRock.grid(column=2, row=6, padx=15)

        Label(
                newAlbumwndw,
                background="black",
                text="Rock",
                fg="#00bf36",
                font="Verdana 10"
        ).grid(column=2, row=6, sticky="s", pady=20)

        bJazz=Button(
                newAlbumwndw,
                background="black",
                width=100,
                height=100,
                image=jazz_black
        )
        bJazz.grid(column=3, row=6, padx=15)

        Label(
                newAlbumwndw,
                background="black",
                text="Jazz",
                fg="#00bf36",
                font="Verdana 10"
        ).grid(column=3, row=6, sticky="s", pady=20)

        bPop=Button(
                newAlbumwndw,
                background="black",
                width=100,
                height=100,
                image=pop_black
        )
        bPop.grid(column=4, row=6, padx=15)

        Label(
                newAlbumwndw,
                background="black",
                text="Pop",
                fg="#00bf36",
                font="Verdana 10"
        ).grid(column=4, row=6, sticky="s", pady=20)

        bClassic=Button(
                newAlbumwndw,
                background="black",
                width=100,
                height=100,
                image=classic_black
        )
        bClassic.grid(column=1, row=7, padx=15, pady=15)

        Label(
                newAlbumwndw,
                background="black",
                text="Clásica",
                fg="#00bf36",
                font="Verdana 10"
        ).grid(column=1, row=7, sticky="s")

        bReggaeton=Button(
                newAlbumwndw,
                background="black",
                width=100,
                height=100,
                image=reggaeton_black
        )
        bReggaeton.grid(column=2, row=7, padx=15)

        Label(
                newAlbumwndw,
                background="black",
                text="Reggaeton",
                fg="#00bf36",
                font="Verdana 10"
        ).grid(column=2, row=7, sticky="s")

        bTrap=Button(
                newAlbumwndw,
                background="black",
                width=100,
                height=100,
                image=trap_black
        )
        bTrap.grid(column=3, row=7, padx=15)

        Label(
                newAlbumwndw,
                background="black",
                text="Trap",
                fg="#00bf36",
                font="Verdana 10"
        ).grid(column=3, row=7, sticky="s")

        bOthers=Button(
                newAlbumwndw,
                background="black",
                width=100,
                height=100,
                image=other_black
        )
        bOthers.grid(column=4, row=7, padx=15)

        Label(
                newAlbumwndw,
                background="black",
                text="Otros",
                fg="#00bf36",
                font="Verdana 10"
        ).grid(column=4, row=7, sticky="s")

        #this is used for knowing which genres has been selected
        genres_selected={}
        genres_selected["electronic"]=[False]
        genres_selected["rock"]=[False]
        genres_selected["jazz"]=[False]
        genres_selected["pop"]=[False]
        genres_selected["classic"]=[False]
        genres_selected["reggaeton"]=[False]
        genres_selected["trap"]=[False]
        genres_selected["others"]=[False]

        #functions

        #when the add_button is clicked it gets the text in the artist input box and add it to the list
        #it also cut the artists String if it is too long
        artistsnames=[]
        def addArtistToList():
                current_artist=inputartist.get("1.0", "end-1c").strip()
                if(current_artist != ''):
                        artistsnames.append(current_artist)

                artists_in_format=''
                cont=1
                for art in artistsnames:
                        artists_in_format+=art
                        if(cont!=len(artistsnames)):
                                artists_in_format+=', '
                        cont+=1

                
                if(len(artists_in_format)>20):
                        artists_in_format_aux=''
                        for x in range(17):
                                artists_in_format_aux+=artists_in_format[x]
                        artists_in_format_aux+='...'
                        artists_in_format=artists_in_format_aux

                artists.configure(text=artists_in_format)                

        #calling the last function in the add_artist button
        add_artist.config(command=addArtistToList)

        #changes the genres images alternating between bright and dark for each one
        def select_deselect(key):
                
                global electronic_icon, electronic_black, rock_icon, rock_black, jazz_icon, jazz_black, pop_icon, pop_black, classic_icon, classic_black, reggaeton_icon, reggaeton_black, trap_icon, trap_black, other_icon, other_black
                if(genres_selected[key]==True):
                        if(key=="electronic"):
                                bElectronic.configure(image=electronic_black)
                                genres_selected[key]=False
                        elif(key=="rock"):
                                bRock.configure(image=rock_black)
                                genres_selected[key]=False
                        elif(key=="pop"):
                                bPop.configure(image=pop_black)
                                genres_selected[key]=False
                        elif(key=="jazz"):
                                bJazz.configure(image=jazz_black)
                                genres_selected[key]=False
                        elif(key=="classic"):
                                bClassic.configure(image=classic_black)
                                genres_selected[key]=False
                        elif(key=="reggaeton"):
                                bReggaeton.configure(image=reggaeton_black)
                                genres_selected[key]=False
                        elif(key=="trap"):
                                bTrap.configure(image=trap_black)
                                genres_selected[key]=False
                        elif(key=="others"):
                                bOthers.configure(image=other_black)
                                genres_selected[key]=False                                                        
                else:
                        if(key=="electronic"):
                                bElectronic.configure(image=electronic_icon)
                                genres_selected[key]=True        
                        elif(key=="rock"):
                                bRock.configure(image=rock_icon)
                                genres_selected[key]=True
                        elif(key=="pop"):
                                bPop.configure(image=pop_icon)
                                genres_selected[key]=True
                        elif(key=="jazz"):
                                bJazz.configure(image=jazz_icon)
                                genres_selected[key]=True
                        elif(key=="classic"):
                                bClassic.configure(image=classic_icon)
                                genres_selected[key]=True
                        elif(key=="reggaeton"):
                                bReggaeton.configure(image=reggaeton_icon)
                                genres_selected[key]=True
                        elif(key=="trap"):
                                bTrap.configure(image=trap_icon)
                                genres_selected[key]=True
                        elif(key=="others"):
                                bOthers.configure(image=other_icon)
                                genres_selected[key]=True 
        
        #calling the select_deselect function for each genre button
        bElectronic.configure(command= lambda : select_deselect("electronic"))
        bRock.configure(command= lambda: select_deselect("rock"))
        bPop.configure(command= lambda: select_deselect("pop"))
        bJazz.configure(command= lambda: select_deselect("jazz"))
        bClassic.configure(command= lambda: select_deselect("classic"))
        bReggaeton.configure(command= lambda: select_deselect("reggaeton"))
        bTrap.configure(command= lambda: select_deselect("trap"))
        bOthers.configure(command= lambda: select_deselect("others"))

        #it opens a new window with errors 
        def errorWindow(message):
                errorwndw = Toplevel(newAlbumwndw)
                errorwndw.title("ERROR")
                errorwndw.geometry("870x250")
                errorwndw['bg']='black'
                Label(
                        errorwndw,
                        text="Se han detectado los siguientes errores:",
                        font="Verdana 25",
                        background="black",
                        fg="red"
                ).grid(row=0, column=0)

                Label(
                        errorwndw,
                        text=message,
                        font="Verdana 12",
                        background="black",
                        fg="white"
                ).grid(row=1, column=0)

                Button(
                        errorwndw,
                        text="Aceptar",
                        background="#00bf36",
                        command= lambda: errorwndw.destroy()
                ).grid(row=2, column=0, pady=20)

        def successfullDisk():
                readXML()
                first_page()
                changeDisksView()
                swndw = Toplevel(newAlbumwndw)
                swndw.title("Creado con éxito")
                swndw.geometry("580x300")
                swndw['bg']='black'
                Label(
                        swndw,
                        text="Tu disco se ha creado con éxito:",
                        font="Verdana 25",
                        background="black",
                        fg="#00bf36"
                ).grid(row=0, column=0)

                Label(
                        swndw,
                        text="Puedes empezar a añadir canciones a \ntu disco desde el menú principal",
                        font="Verdana 12",
                        background="black",
                        fg="#00bf36"
                ).grid(row=1, column=0, pady=40)

                Button(
                        swndw,
                        text="Aceptar",
                        background="#00bf36",
                        command= lambda: swndw.destroy()
                ).grid(row=2, column=0, pady=20)

        #obtaining current Year
        currentDateTime = datetime.datetime.now()
        date = currentDateTime.date()
        year = date.strftime("%Y")

        #when the create button is clicked it checks all the data and creates a new disk in the xml
        def diskCreator():
                allOk=True
                errorMessage=''
                if(len(artistsnames)==0):
                        errorMessage+="\n*El nuevo disco tiene que tener algún artista asociado."
                        allOk=False

                year_aux=inputyear.get("1.0", "end-1c")        
                
                if(year_aux.isnumeric()):
                        if(int(year_aux)>int(year) or int(year_aux)<0 ):
                                errorMessage+="\n*El año debe ser un entero entre 0 y "+str(year)
                else:
                        errorMessage+="\n*El año no se corresponde con un número entero. Comprueba que se trata de un entero entre 0 y "+str(year)
                        allOk=False                

                disk_name_aux=inputname.get("1.0", "end-1c").strip()
                if(len(disk_name_aux)==0):
                        errorMessage+="\n*El disco debe tener un nombre asociado"
                        allOk=False

                anyGenreSelected=False
                
                for g in genres_selected:
                        if(genres_selected[g]==True):
                                anyGenreSelected=True
                                break

                if(anyGenreSelected==False):
                        errorMessage+="\n*El disco debe tener asociado al menos un género musical"
                        allOk=False

                if(allOk==False):
                        errorWindow(errorMessage)
                else:
                        disc=ET.SubElement(root, 'disco')
                        disc.set('nombre', disk_name_aux)
                        disc.set('anho', year_aux)
                        disc.set('imagen', "")
                        for g in genres_selected:
                                if(genres_selected[g]==True):
                                        genre=ET.SubElement(disc, 'genero')
                                        genre.text=g
                        
                        for a in artistsnames:
                                artist=ET.SubElement(disc,'artista')
                                artist.text=a
                        songs=ET.SubElement(disc, 'canciones')                        
                        tree.write(filename)

                        successfullDisk()

        bok.config(command=diskCreator)

"""
here ends the disco creator window
***********************************************************************************************************************************************
"""
#adding the new disk window to its button
l1newdisk.configure(command=windowNewAlbum)  

#function to delete disks
def deleteDisk(pos):
        global page, root, tree, filename
        pos_in_audiolibrary=(page*6)+pos
        
        alertwndw = Toplevel(wndw)
        alertwndw.title("¡¡¡Atención!!!")
        alertwndw.geometry("280x200")
        alertwndw['bg']='black'
        
        Label(
                alertwndw,
                text="Atención:",
                font="Verdana 25",
                background="black",
                fg="yellow"
        ).grid(row=0, column=0, columnspan=2)
        
        disk_remove_name="disco"

        Label(
                alertwndw,
                text="Está a punto de eliminar el disco\n" + disk_remove_name+"\n¿Está segur@?",
                font="Verdana 12",
                background="black",
                fg="white"
        ).grid(row=1, column=0, columnspan=2)

        def remove():
                root.remove(root[pos_in_audiolibrary])
                tree.write(filename)
                readXML()
                first_page()
                changeDisksView()
                alertwndw.destroy()

        Button(
                alertwndw,
                text="Borrar disco",
                background="red",
                command=remove
        ).grid(row=2, column=0, pady=30)

        Button(
                alertwndw,
                text="Déjalo estar",
                background="#00bf36",
                command=lambda: alertwndw.destroy()
        ).grid(row=2, column=1)        



list_p[0].configure(command=lambda:deleteDisk(0))
list_p[1].configure(command=lambda:deleteDisk(1))
list_p[2].configure(command=lambda:deleteDisk(2))
list_p[3].configure(command=lambda:deleteDisk(3))
list_p[4].configure(command=lambda:deleteDisk(4))
list_p[5].configure(command=lambda:deleteDisk(5))        



"""
songs of the disks interface
***********************************************************************************************************************************************
"""
def disckInferface(pos, alert):

        global page, root, tree

        #disk interface configuration
        diskwndw = Toplevel(wndw)
        diskwndw.title("DISCO")
        diskwndw.geometry("800x900")
        diskwndw['bg']='black'
        pos_in_audiolibrary=(page*6)+pos

        #function if the user adds a song 
        def createSuccessfull():
                s2wndw = Toplevel(diskwndw)
                s2wndw.title("Creado con éxito")
                s2wndw.geometry("580x300")
                s2wndw['bg']='black'
                Label(
                        s2wndw,
                        text="Tu canción se ha creado con éxito:",
                        font="Verdana 25",
                        background="black",
                        fg="#00bf36"
                ).grid(row=0, column=0)

                Label(
                        s2wndw,
                        text="Por el momento la música no está implementada.\nSe guardara la ruta a la canción si se ha introducido",
                        font="Verdana 12",
                        background="black",
                        fg="#00bf36"
                ).grid(row=1, column=0, pady=40)

                Button(
                        s2wndw,
                        text="Aceptar",
                        background="#00bf36",
                        command= lambda: s2wndw.destroy()
                ).grid(row=2, column=0, pady=20)
        
        if(alert==True):
                createSuccessfull()

        #labels just to center all
        Label(
                diskwndw,
                background="black",
                image=arrow_icon,
                width=100,
                height=100
        ).grid(row=0, column=0)

        Label(
                diskwndw,
                background="white",
                image=delete_icon,
                width=155,
                height=155
        ).grid(row=1, column=1, rowspan=3, columnspan=2, padx=20)

        #top of interface (image, year, diskname, artists and some buttons)
        limage_disk=Label(
                diskwndw,
                background="black",
                image=icon_cd,
                width=150,
                height=150
        )
        limage_disk.grid(row=1, column=1, rowspan=3, columnspan=2)


        Label(
                diskwndw,
                text=root[pos_in_audiolibrary].attrib["anho"],
                background="white",
                fg="white",
                font="Verdana 17"
        ).grid(row=1, column=1)

        lyear=Label(
                diskwndw,
                text=root[pos_in_audiolibrary].attrib["anho"],
                background="black",
                fg="#00bf36",
                font="Verdana 15"
        )
        lyear.grid(row=1, column=1)

        ltitle=Label(
                diskwndw,
                text=root[pos_in_audiolibrary].attrib["nombre"],
                background="black",
                fg="#00bf36",
                font="Verdana 30"
        )
        ltitle.grid(row=1, column=3, columnspan=5)

        bedit=Button(
                diskwndw,
                background="black",
                image=edit_icon,
                width=45,
                height=45, 
                border=0
        )
        bedit.grid(column=8, row=1, pady=10)

        #genres availables in disk

        lg1=Label(
                diskwndw,
                background="black",
                image=arrow_icon,
                width=20,
                height=20 
        )
        lg1.grid(row=2, column=3, sticky="s", pady=5, padx=5)

        lg2=Label(
                diskwndw,
                background="black",
                image=arrow_icon,
                width=20,
                height=20 
        )
        lg2.grid(row=2, column=4, pady=5, sticky="s")

        lg3=Label(
                diskwndw,
                background="black",
                image=arrow_icon,
                width=20,
                height=20 
        )
        lg3.grid(row=2, column=5, sticky="s", pady=5, padx=5)

        lg4=Label(
                diskwndw,
                background="black",
                image=arrow_icon,
                width=20,
                height=20 
        )
        lg4.grid(row=2, column=6, sticky="s", pady=5)

        lg5=Label(
                diskwndw,
                background="black",
                image=arrow_icon,
                width=20,
                height=20 
        )
        lg5.grid(row=3, column=3, sticky="n")

        lg6=Label(
                diskwndw,
                background="black",
                image=arrow_icon,
                width=20,
                height=20 
        )
        lg6.grid(row=3, column=4, sticky="n")

        lg7=Label(
                diskwndw,
                background="black",
                image=arrow_icon,
                width=20,
                height=20 
        )
        lg7.grid(row=3, column=5, sticky="n")

        lg8=Label(
                diskwndw,
                background="black",
                image=arrow_icon,
                width=20,
                height=20 
        )
        lg8.grid(row=3, column=6, sticky="n")

        
        #genres view
        for genero in root[pos_in_audiolibrary].iter("genero"):
                if(genero.text=="electronic"):
                        lg1.configure(background="white", image=electronic_mini_icon)
                elif(genero.text=="rock"):
                        lg2.configure(background="white", image=rock_mini_icon)
                elif(genero.text=="jazz"):
                        lg3.configure(background="white", image=jazz_mini_icon)
                elif(genero.text=="pop"):
                        lg4.configure(background="white", image=pop_mini_icon)
                elif(genero.text=="classic"):
                        lg5.configure(background="white", image=classic_mini_icon)
                elif(genero.text=="reggaeton"):                                      
                        lg6.configure(background="white", image=reggaeton_mini_icon)
                elif(genero.text=="trap"):
                        lg7.configure(background="white", image=trap_mini_icon)
                else:
                        lg8.configure(background="white", image=other_mini_icon)            

        #artists output
        artists_long=""
        count_artist=0
        for a in root[pos_in_audiolibrary].iter('artista'):
                count_artist+=1
                if(count_artist==1):
                        artists_long+=a.text
                elif(count_artist<4):
                        artists_long+="\n"+a.text

        if(count_artist>3):
                artists_long+="\ny "+str(count_artist-3)+" más"

        lartists=Label(
                diskwndw,
                text=artists_long,
                background="black",
                fg="#00bf36",
                font="Verdana 15"
        )        
        lartists.grid(row=2, column=7, rowspan=2, padx=20)

        bmore_artists=Button(
                diskwndw,
                background="white",
                image=more_artists_icon,
                width=35,
                height=35
        )
        bmore_artists.grid(column=8, row=3)

        if(count_artist<4):
                bmore_artists.grid_forget()

        #button for adding more songs to the current disk
        badd_songs=Button(
                diskwndw,
                background="white",
                image=more_songs_icon,
                width=35,
                height=35
        )
        if (count_artist<4):
                badd_songs.grid(column=1, row=4, pady=15)                        
        else:
                badd_songs.grid(column=1, row=4)  
        
        #number of songs
        count_songs=0
        
        for s in root[pos_in_audiolibrary].iter('cancion'):
                count_songs+=1

        #songs interface (play and pause button, white background, song name, sound icon, edit icon)*4
        Label(
                diskwndw,
                background="black",
                height=3,
                width=70
        ).grid(row=5, column=1, columnspan=7, sticky="e")

        base1=Label(
                diskwndw,
                background="white",
                height=4,
                width=70
        )
        base1.grid(row=6, column=1, columnspan=7, pady=15, sticky="e")

        ns1=Label(
                diskwndw,
                image=no_sound_icon,
                width=55,
                height=55,
                background="white",
                border=0
        )
        ns1.grid(row=6, column=7, sticky="e")

        bps1=Button(
                diskwndw,
                image=play_icon,
                width=45,
                height=45,
                borderwidth=0,
                background="white"
        )
        bps1.grid(row=6, column=1)

        ls1=Label(
                diskwndw,
                text="cancion 1",
                font="Verdana 15",
                background="white"
        )
        ls1.grid(row=6, column=2, columnspan=5, sticky="w")

        bedit1=Button(
                diskwndw,
                background="black",
                image=edit_icon,
                width=45,
                height=45,
                border=0
        )
        bedit1.grid(column=8, row=6, pady=10)

        bdelete1=Button(
                diskwndw,
                background="black",
                image=delete_45_icon,
                width=45,
                height=45,
                border=0
        )
        bdelete1.grid(column=9, row=6, pady=10)

        base2=Label(
                diskwndw,
                background="white",
                height=4,
                width=70,
                borderwidth=0
        )
        base2.grid(row=7, column=1, columnspan=7, pady=15, sticky="e")

        ns2=Label(
                diskwndw,
                image=no_sound_icon,
                width=55,
                height=55,
                background="white",
                border=0
        )
        ns2.grid(row=7, column=7, sticky="e")

        bps2=Button(
                diskwndw,
                image=play_icon,
                width=45,
                height=45,
                borderwidth=0,
                background="white"
        )
        bps2.grid(row=7, column=1)

        ls2=Label(
                diskwndw,
                text="cancion 1",
                font="Verdana 15",
                background="white"
        )
        ls2.grid(row=7, column=2, columnspan=5, sticky="w")

        bedit2=Button(
                diskwndw,
                background="black",
                image=edit_icon,
                width=45,
                height=45,
                border=0
        )
        bedit2.grid(column=8, row=7, pady=10)

        bdelete2=Button(
                diskwndw,
                background="black",
                image=delete_45_icon,
                width=45,
                height=45,
                border=0
        )
        bdelete2.grid(column=9, row=7, pady=10)

        base3=Label(
                diskwndw,
                background="white",
                height=4,
                width=70
        )
        base3.grid(row=8, column=1, columnspan=7, pady=15, sticky="e")

        ns3=Label(
                diskwndw,
                image=no_sound_icon,
                width=55,
                height=55,
                background="white",
                border=0
        )
        ns3.grid(row=8, column=7, sticky="e")

        bps3=Button(
                diskwndw,
                image=play_icon,
                width=45,
                height=45,
                borderwidth=0,
                background="white"
        )
        bps3.grid(row=8, column=1)

        ls3=Label(
                diskwndw,
                text="cancion 1",
                font="Verdana 15",
                background="white"
        )
        ls3.grid(row=8, column=2, columnspan=5, sticky="w")

        bedit3=Button(
                diskwndw,
                background="black",
                image=edit_icon,
                width=45,
                height=45,
                border=0
        )
        bedit3.grid(column=8, row=8, pady=10)

        bdelete3=Button(
                diskwndw,
                background="black",
                image=delete_45_icon,
                width=45,
                height=45,
                border=0
        )
        bdelete3.grid(column=9, row=8, pady=10)

        base4=Label(
                diskwndw,
                background="white",
                height=4,
                width=70,
                borderwidth=0
        )
        base4.grid(row=9, column=1, columnspan=7, pady=15, sticky="e")

        ns4=Label(
                diskwndw,
                image=no_sound_icon,
                width=55,
                height=55,
                background="white",
                border=0
        )
        ns4.grid(row=9, column=7, sticky="e")

        bps4=Button(
                diskwndw,
                image=play_icon,
                width=45,
                height=45,
                borderwidth=0,
                background="white"
        )
        bps4.grid(row=9, column=1)

        ls4=Label(
                diskwndw,
                text="cancion 1",
                font="Verdana 15",
                background="white"
        )
        ls4.grid(row=9, column=2, columnspan=5, sticky="w")

        bedit4=Button(
                diskwndw,
                background="black",
                image=edit_icon,
                width=45,
                height=45,
                border=0
        )
        bedit4.grid(column=8, row=9, pady=10)

        bdelete4=Button(
                diskwndw,
                background="black",
                image=delete_45_icon,
                width=45,
                height=45,
                border=0
        )
        bdelete4.grid(column=9, row=9, pady=10)

        #right and left buttons

        bRight=Button(
                diskwndw,
                image=arrow_icon,
                width=30,
                height=30
        )
        bRight.grid(column=7, row=10, sticky="se", pady=20)

        bLeft=Button(
                diskwndw,
                image=arrowLeft_icon,
                width=30,
                height=30
        )
        bLeft.grid(column=1, row=10, sticky="sw", pady=20)

        bLeft.grid_forget()

        #configuring deppending on the number of songs
        if(count_songs<5):
                bRight.grid_forget()
        if(count_songs<4):
                base4.grid_forget()
                ns4.grid_forget()
                bps4.grid_forget()
                ls4.grid_forget()
                bedit4.grid_forget()
                bdelete4.grid_forget()
        if(count_songs<3):
                base3.grid_forget()
                ns3.grid_forget()
                bps3.grid_forget()
                ls3.grid_forget()
                bedit3.grid_forget()
                bdelete3.grid_forget()
        if(count_songs<2):
                base2.grid_forget()
                ns2.grid_forget()
                bps2.grid_forget()
                ls2.grid_forget()
                bedit2.grid_forget()
                bdelete2.grid_forget()
        if(count_songs<1):
                base1.grid_forget()
                ns1.grid_forget()
                bps1.grid_forget()
                ls1.grid_forget()
                bedit1.grid_forget()
                bdelete1.grid_forget() 

        def cutString(st):
                toret=""
                c=0
                for l in st:
                        if(c<12):
                                toret+=l
                        else:
                                break
                        c+=1

                if(len(st)>12):
                        toret+="..."
                return toret        

        #list with all the names of the songs
        songs_names=[]

        for s in root[pos_in_audiolibrary].iter("cancion"):
                songs_names.append(cutString(s.attrib["nombre"]))

        songs_names.append("here no song")
        songs_names.append("here no song") 
        songs_names.append("here no song") 
        songs_names.append("here no song")         

        if(count_songs>=1):
                ls1.config(text=songs_names[0])

        if(count_songs>=2):
                ls2.config(text=songs_names[1]) 

        if(count_songs>=3):
                ls3.config(text=songs_names[2])

        if(count_songs>=4):
                ls4.config(text=songs_names[3])                       

        #by now only change the image. In a future it will reproduce mp3s 
        def pauseOrStop(pos, newState):
                if(newState=="pause"):
                        if(pos==1):
                                bps1.configure(image=play_icon)
                                bps1.configure(command=lambda: pauseOrStop(1, "play"))
                        if(pos==2):
                                bps2.configure(image=play_icon)
                                bps2.configure(command=lambda: pauseOrStop(2, "play"))
                        if(pos==3):
                                bps3.configure(image=play_icon)
                                bps3.configure(command=lambda: pauseOrStop(3, "play"))
                        if(pos==4):
                                bps4.configure(image=play_icon)
                                bps4.configure(command=lambda: pauseOrStop(4, "play"))
                elif(newState=="play"):
                        bps1.configure(image=play_icon)
                        bps1.configure(command=lambda: pauseOrStop(1, "play"))
                       
                        bps2.configure(image=play_icon)
                        bps2.configure(command=lambda: pauseOrStop(2, "play"))
                        
                        bps3.configure(image=play_icon)
                        bps3.configure(command=lambda: pauseOrStop(3, "play"))
                        
                        bps4.configure(image=play_icon)
                        bps4.configure(command=lambda: pauseOrStop(4, "play"))

                        if(pos==1):
                                bps1.configure(image=pause_icon)
                                bps1.configure(command=lambda: pauseOrStop(1, "pause"))
                        if(pos==2):
                                bps2.configure(image=pause_icon)
                                bps2.configure(command=lambda: pauseOrStop(2, "pause"))
                        if(pos==3):
                                bps3.configure(image=pause_icon)
                                bps3.configure(command=lambda: pauseOrStop(3, "pause"))
                        if(pos==4):
                                bps4.configure(image=pause_icon)
                                bps4.configure(command=lambda: pauseOrStop(4, "pause"))        


        bps1.configure(command=lambda: pauseOrStop(1, "play"))
        bps2.configure(command=lambda: pauseOrStop(2, "play"))
        bps3.configure(command=lambda: pauseOrStop(3, "play"))
        bps4.configure(command=lambda: pauseOrStop(4, "play"))

        #page that is showed page0 = pos0, pos1, pos2, pos3 // page1= pos4, pos5, pos6, pos7 // page2= pos8, pos9, pos10, pos11 ...
        global pageSong
        pageSong=0

        #True for right, False for left
        def changeSongs(r):
                global pageSong

                pauseOrStop(1, "pause")
                pauseOrStop(2, "pause")
                pauseOrStop(3, "pause")
                pauseOrStop(4, "pause")

                if (r==True):
                        pageSong=pageSong+1
                else:
                        pageSong=pageSong-1

                if(count_songs>=(pageSong*4)+1):
                        ls1.config(text=songs_names[pageSong*4])
                        base1.grid(row=6, column=1, columnspan=7, pady=15, sticky="e")
                        ns1.grid(row=6, column=7, sticky="e")
                        bps1.grid(row=6, column=1)
                        ls1.grid(row=6, column=2, columnspan=5, sticky="w")
                        bedit1.grid(column=8, row=6, pady=10)
                else:
                        base1.grid_forget()
                        ns1.grid_forget()
                        bps1.grid_forget()
                        ls1.grid_forget()
                        bedit1.grid_forget()


                if(count_songs>=(pageSong*4)+2):
                        ls2.config(text=songs_names[(pageSong*4)+1])
                        base2.grid(row=7, column=1, columnspan=7, pady=15, sticky="e")
                        ns2.grid(row=7, column=7, sticky="e")
                        bps2.grid(row=7, column=1)
                        ls2.grid(row=7, column=2, columnspan=5, sticky="w")
                        bedit2.grid(column=8, row=7, pady=10) 
                else:
                        base2.grid_forget()
                        ns2.grid_forget()
                        bps2.grid_forget()
                        ls2.grid_forget()
                        bedit2.grid_forget()        

                if(count_songs>=(pageSong*4)+3):
                        ls3.config(text=songs_names[(pageSong*4)+2])
                        base3.grid(row=8, column=1, columnspan=7, pady=15, sticky="e")
                        ns3.grid(row=8, column=7, sticky="e")
                        bps3.grid(row=8, column=1)
                        ls3.grid(row=8, column=2, columnspan=5, sticky="w")
                        bedit3.grid(column=8, row=8, pady=10)

                else:
                        base3.grid_forget()
                        ns3.grid_forget()
                        bps3.grid_forget()
                        ls3.grid_forget()
                        bedit3.grid_forget()         

                if(count_songs>=(pageSong*4)+4):
                        ls4.config(text=songs_names[(pageSong*4)+3])
                        base4.grid(row=9, column=1, columnspan=7, pady=15, sticky="e")
                        ns4.grid(row=9, column=7, sticky="e")
                        bps4.grid(row=9, column=1)
                        ls4.grid(row=9, column=2, columnspan=5, sticky="w")
                        bedit4.grid(column=8, row=9, pady=10)  
                else:
                        base4.grid_forget()
                        ns4.grid_forget()
                        bps4.grid_forget()
                        ls4.grid_forget()
                        bedit4.grid_forget() 

                if(count_songs> pageSong*4+4):
                        bRight.grid(column=7, row=10, sticky="se", pady=20)
                else:
                        bRight.grid_forget()

                if(pageSong==0):
                        bLeft.grid_forget()
                else: 
                        bLeft.grid(column=1, row=10, sticky="sw", pady=20)

        bRight.configure(command=lambda: changeSongs(True))
        bLeft.configure(command=lambda: changeSongs(False))

        #New song interface
        def createSong():
                newsongwndw = Toplevel(diskwndw)
                newsongwndw.title("Crea aquí tu canción")
                newsongwndw.geometry("800x700")
                newsongwndw['bg']='black'
                
                Label(
                        newsongwndw,
                        font="Verdana 50",
                        background="black",
                        fg="black",
                        text="O"
                ).grid(row=0, column=0)

                Label(
                        newsongwndw,
                        text="Crea e importa tu canción",
                        font="Verdana 40",
                        background="black",
                        fg="#00bf36"  
                ).grid(row=1, column=1, columnspan=2, pady=30)

                Label(
                        newsongwndw,
                        text="Nombre :",
                        font="Verdana 20",
                        background="black",
                        fg="#00bf36"  
                ).grid(row=2, column=1, pady=30)

                Label(
                        newsongwndw,
                        text="MP3 :",
                        font="Verdana 20",
                        background="black",
                        fg="#00bf36"  
                ).grid(row=3, column=1, pady=30)

                inputSname = Text(
                        newsongwndw,
                        height = 1,
                        width = 20,
                        font="Verdana 30"
                )
                inputSname.grid(row=2, column=2, sticky="w")

                findSong = Button(
                        newsongwndw,
                        height=60,
                        width=60,
                        image=find_song_icon,
                        border=0,
                        background="black"
                )
                findSong.grid(row=3, column=2, sticky="w")

                Label(
                        newsongwndw,
                        text="Duración :",
                        font="Verdana 20",
                        background="black",
                        fg="#00bf36" 
                ).grid(column=1, row=4, pady=30)

                lduration=Label(
                        newsongwndw,
                        text="future implementation : Def: 3:00",
                        font="Verdana 20",
                        background="black",
                        fg="#00bf36"
                )

                lduration.grid(row=4, column=2, sticky="w")

                bCreate=Button(
                        newsongwndw,
                        background="#00bf36",
                        text="Crear",
                        font="Verdana 15"       
                )
                bCreate.grid(row="5", column="2", pady=30, sticky="w")

                Button(
                        newsongwndw,
                        background="grey",
                        text="Cancelar",
                        font="Verdana 15",
                        command= lambda: newsongwndw.destroy()       
                ).grid(row="5", column="2", sticky="e", padx=100)

                linkmp3=""
                def askMP3():
                        global linkmp3
                        filename=filedialog.askopenfilename(title = "Select file",filetypes = (('mp3 Files.', '*.mp3'),))
                        if(filename!=""):
                                newfilename="./src/mp3/"
                                tail=os.path.split(filename)
                                newfilename+=tail[1]
                                if(os.path.exists(newfilename)==False):
                                        shutil.copy(filename, newfilename)
                        linkmp3=newfilename


                findSong.configure(command=askMP3)

                def alertSong():
                        alertwndw = Toplevel(newsongwndw)
                        alertwndw.title("Alerta")
                        alertwndw.geometry("870x250")
                        alertwndw['bg']='black'
                        Label(
                                alertwndw,
                                text="Alerta:",
                                font="Verdana 25",
                                background="black",
                                fg="red"
                        ).grid(row=0, column=0)

                        Label(
                                alertwndw,
                                text="La canción debe tener al menos el nombre",
                                font="Verdana 12",
                                background="black",
                                fg="white"
                        ).grid(row=1, column=0)

                        Button(
                                alertwndw,
                                background="#00bf36",
                                text="Aceptar",
                                font="Verdana 15",
                                command= lambda: alertwndw.destroy()
                        ).grid(row=2, column=0)

                def create():

                        song_name=inputSname.get("1.0", "end-1c").strip()

                        if(song_name==""):
                                alertSong()
                        else:
                                for canciones in root[pos_in_audiolibrary].iter("canciones"):
                                        c=ET.SubElement(canciones, "cancion")
                                        c.set('nombre', song_name)
                                        c.set('duracion', '0:00')
                                        c.set('link', linkmp3)
                                        tree.write(filename)
                                diskwndw.destroy()
                                disckInferface(pos, True)

                                

                bCreate.configure(command=create)                                

        badd_songs.configure(command=createSong)
        
        #interface for deletting song
        def deleteSong(posaux):
                alertwndw = Toplevel(diskwndw)
                alertwndw.title("¡¡¡Atención!!!")
                alertwndw.geometry("300x200")
                alertwndw['bg']='black'

                song_position=(pageSong*4)+posaux
                
                Label(
                        alertwndw,
                        text="Atención:",
                        font="Verdana 25",
                        background="black",
                        fg="yellow"
                ).grid(row=0, column=0, columnspan=2)

                Label(
                        alertwndw,
                        text="Está a punto de eliminar la canción",
                        font="Verdana 12",
                        background="black",
                        fg="white"
                ).grid(row=1, column=0, columnspan=2)

                def remove2():
                        for canciones in root[pos_in_audiolibrary].iter("canciones"):
                                canciones.remove(canciones[song_position]) 
                        tree.write(filename)
                        diskwndw.destroy()
                        disckInferface(pos, False)

                Button(
                        alertwndw,
                        text="Borrar canción",
                        background="red",
                        command=remove2
                ).grid(row=2, column=0, pady=30)

                Button(
                        alertwndw,
                        text="Déjala estar",
                        background="#00bf36",
                        command=lambda: alertwndw.destroy()
                ).grid(row=2, column=1)

        bdelete1.configure(command=lambda: deleteSong(0))
        bdelete2.configure(command=lambda: deleteSong(1))
        bdelete3.configure(command=lambda: deleteSong(2))
        bdelete4.configure(command=lambda: deleteSong(3))        

        #edit song interface
        def editSongInterface(posaux):
                global songLink
                songPos=(pageSong*4)+posaux
                songName=""
                songDuration=""
                songLink=""
                for canciones in root[pos_in_audiolibrary].iter("canciones"):
                        songName=canciones[songPos].attrib["nombre"]
                        songDuration=canciones[songPos].attrib["duracion"]
                        songLink=canciones[songPos].attrib["link"]
                        break

                editsongwndw = Toplevel(diskwndw)
                editsongwndw.title("Crea aquí tu canción")
                editsongwndw.geometry("800x700")
                editsongwndw['bg']='black'
                
                Label(
                        editsongwndw,
                        text="O",
                        background="black",
                        font="Verdana 50",
                        fg="black"
                ).grid(row=0, column=0)

                Label(
                        editsongwndw,
                        text="Edita aquí tu canción",
                        font="Verdana 40",
                        background="black",
                        fg="#00bf36"
                ).grid(row=1, column=1, columnspan=3, pady=30)

                Label(
                        editsongwndw,
                        text="nombre :",
                        font="Verdana 20",
                        background="black",
                        fg="#00bf36"
                ).grid(row=2, column=1)

                Label(
                        editsongwndw,
                        text="link :",
                        font="Verdana 20",
                        background="black",
                        fg="#00bf36"
                ).grid(row=3, column=1)

                Label(
                        editsongwndw,
                        text="duración :",
                        font="Verdana 20",
                        background="black",
                        fg="#00bf36"
                ).grid(row=4, column=1)

                inputName=Text(
                        editsongwndw,
                        height=1,
                        width=20,
                        font="Verdana 20"
                )
                inputName.grid(row=2, column=2, columnspan=2)
                inputName.insert(INSERT, songName)

                changeSong=Button(
                        editsongwndw,
                        height=60,
                        width=60,
                        image=find_song_icon,
                        border=0,
                        background="black"
                )
                changeSong.grid(row=3, column=3, sticky="e", padx=60)
                
                lLink=Label(
                        editsongwndw,
                        text=songLink,
                        font="Verdana 20",
                        background="black",
                        fg="#00bf36"
                )
                lLink.grid(row=3, column=2, pady=30)

                if(songLink==""):
                        lLink.configure(text="no file found")

                lDuracion=Label(
                        editsongwndw,
                        text=songDuration,
                        font="Verdana 20",
                        background="black",
                        fg="#00bf36"
                )
                lDuracion.grid(row=4, column=2)

                bSaveAndExit=Button(
                        editsongwndw,
                        background="#00bf36",
                        font="Verdana 20",
                        text="Guardar"
                )
                bSaveAndExit.grid(row=5, column=1)

                Button(
                        editsongwndw,
                        font="Verdana 20",
                        text="Cancelar",
                        background="grey",
                        fg="black",
                        command= lambda: editsongwndw.destroy()
                ).grid(row=5, column=3, pady=30)

                def searchMP32():
                        global songLink
                        filename=filedialog.askopenfilename(title = "Select file",filetypes = (('mp3 Files.', '*.mp3'),))
                        if(filename!=""):
                                newfilename="./src/mp3/"
                                tail=os.path.split(filename)
                                newfilename+=tail[1]
                                if(os.path.exists(newfilename)==False):
                                        shutil.copy(filename, newfilename)
                        songLink=newfilename

                        lLink.configure(text=songLink)

                changeSong.configure(command=lambda: searchMP32())        

                def saveAndExit():
                        global songLink
                        namenew=inputName.get("1.0", "end-1c")
                        for canciones in root[pos_in_audiolibrary].iter("canciones"):
                                if(namenew==""):
                                        namenew=songName
                                canciones[songPos].set('nombre', namenew)
                                canciones[songPos].set('link', songLink)
                        tree.write(filename)
                        editsongwndw.destroy()
                        diskwndw.destroy()
                        disckInferface(pos, False)

                bSaveAndExit.configure(command=lambda: saveAndExit())

        #interface with the options to edit
        def editDiskInteface():
                editdiskwndw = Toplevel(diskwndw)
                editdiskwndw.title("Artistas")
                editdiskwndw.geometry("800x700")
                editdiskwndw['bg']='black'

                Label(
                        editdiskwndw,
                        font="Verdana 50",
                        background="black",
                        text="O"
                ).grid(row=0, column=0)

                Label(
                        editdiskwndw,
                        font="Verdana 30",
                        background="black",
                        text="Edita aquí tu disco",
                        fg="#00bf36"
                ).grid(row=1, column=1, columnspan=4)

                Label(
                        editdiskwndw,
                        font="Verdana 20",
                        background="black",
                        text="Nombre :",
                        fg="#00bf36"
                ).grid(row=2, column=1)

                inputNewName = Text(
                        editdiskwndw,
                        height = 1,
                        width = 15,
                        font="Verdana 30"
                )
                inputNewName.grid(row=2, column=2, columnspan=3, pady="30")
                oldName=root[pos_in_audiolibrary].attrib["nombre"]
                inputNewName.insert(INSERT, oldName)

                Label(
                        editdiskwndw,
                        font="Verdana 20",
                        background="black",
                        text="Artistas :",
                        fg="#00bf36"
                ).grid(row=3, column=1)

                bedit_artists=Button(
                        editdiskwndw,
                        image=edit_icon,
                        width=45,
                        height=45
                )
                bedit_artists.grid(row=3, column=2)

                Label(
                        editdiskwndw,
                        font="Verdana 20",
                        background="black",
                        text="Año :",
                        fg="#00bf36"
                ).grid(row=4, column=1)

                inputNewYear = Text(
                        editdiskwndw,
                        height = 1,
                        width = 15,
                        font="Verdana 30"
                )
                inputNewYear.grid(row=4, column=2, columnspan=3, pady=30)
                oldYear=root[pos_in_audiolibrary].attrib["anho"]
                inputNewYear.insert(INSERT, oldYear)

                Label(
                        editdiskwndw,
                        font="Verdana 20",
                        background="black",
                        text="Géneros :",
                        fg="#00bf36"
                ).grid(row=5, column=1)


                bElectronic=Button(
                        editdiskwndw,
                        background="black",
                        width=100,
                        height=100,
                        image=electronic_black
                )
                bElectronic.grid(column=1, row=6, pady=30, padx=15)

                Label(
                        editdiskwndw,
                        background="black",
                        text="Electrónica",
                        fg="#00bf36",
                        font="Verdana 10"
                ).grid(column=1, row=6, sticky="s", pady=20)

                bRock=Button(
                        editdiskwndw,
                        background="black",
                        width=100,
                        height=100,
                        image=rock_black
                )
                bRock.grid(column=2, row=6, padx=15)

                Label(
                        editdiskwndw,
                        background="black",
                        text="Rock",
                        fg="#00bf36",
                        font="Verdana 10"
                ).grid(column=2, row=6, sticky="s", pady=20)

                bJazz=Button(
                        editdiskwndw,
                        background="black",
                        width=100,
                        height=100,
                        image=jazz_black
                )
                bJazz.grid(column=3, row=6, padx=15)

                Label(
                        editdiskwndw,
                        background="black",
                        text="Jazz",
                        fg="#00bf36",
                        font="Verdana 10"
                ).grid(column=3, row=6, sticky="s", pady=20)

                bPop=Button(
                        editdiskwndw,
                        background="black",
                        width=100,
                        height=100,
                        image=pop_black
                )
                bPop.grid(column=4, row=6, padx=15)

                Label(
                        editdiskwndw,
                        background="black",
                        text="Pop",
                        fg="#00bf36",
                        font="Verdana 10"
                ).grid(column=4, row=6, sticky="s", pady=20)

                bClassic=Button(
                        editdiskwndw,
                        background="black",
                        width=100,
                        height=100,
                        image=classic_black
                )
                bClassic.grid(column=1, row=7, padx=15, pady=15)

                Label(
                        editdiskwndw,
                        background="black",
                        text="Clásica",
                        fg="#00bf36",
                        font="Verdana 10"
                ).grid(column=1, row=7, sticky="s")

                bReggaeton=Button(
                        editdiskwndw,
                        background="black",
                        width=100,
                        height=100,
                        image=reggaeton_black
                )
                bReggaeton.grid(column=2, row=7, padx=15)

                Label(
                        editdiskwndw,
                        background="black",
                        text="Reggaeton",
                        fg="#00bf36",
                        font="Verdana 10"
                ).grid(column=2, row=7, sticky="s")

                bTrap=Button(
                        editdiskwndw,
                        background="black",
                        width=100,
                        height=100,
                        image=trap_black
                )
                bTrap.grid(column=3, row=7, padx=15)

                Label(
                        editdiskwndw,
                        background="black",
                        text="Trap",
                        fg="#00bf36",
                        font="Verdana 10"
                ).grid(column=3, row=7, sticky="s")

                bOthers=Button(
                        editdiskwndw,
                        background="black",
                        width=100,
                        height=100,
                        image=other_black
                )
                bOthers.grid(column=4, row=7, padx=15)

                Label(
                        editdiskwndw,
                        background="black",
                        text="Otros",
                        fg="#00bf36",
                        font="Verdana 10"
                ).grid(column=4, row=7, sticky="s")
                
                genres_selected=[False, False, False, False, False, False, False, False]

                for genero in root[0].iter("genero"):
                        gName=genero.text
                        if(gName=="electronic"):
                                genres_selected[0]=True
                                bElectronic.configure(image=electronic_icon)
                        elif(gName=="rock"):
                                genres_selected[1]=True
                                bRock.configure(image=rock_icon)
                        elif(gName=="jazz"):
                                genres_selected[2]=True
                                bJazz.configure(image=jazz_icon)
                        elif(gName=="pop"):
                                genres_selected[3]=True
                                bPop.configure(image=pop_icon)
                        elif(gName=="classic"):
                                genres_selected[4]=True
                                bClassic.configure(image=classic_icon)
                        elif(gName=="reggaeton"):
                                genres_selected[5]=True
                                bReggaeton.configure(image=reggaeton_icon)
                        elif(gName=="trap"):
                                genres_selected[6]=True
                                bTrap.configure(image=trap_icon)
                        else:
                                genres_selected[7]=True  
                                bOthers.configure(image=other_icon)


                def changeLightDark(posgenre):
                        if(genres_selected[posgenre]==True):
                                genres_selected[posgenre]=False
                        else:
                                genres_selected[posgenre]=True

                        if(posgenre==0):
                                if(genres_selected[posgenre]==True):
                                        bElectronic.configure(image=electronic_icon)
                                else:
                                        bElectronic.configure(image=electronic_black)
                        if(posgenre==1):
                                if(genres_selected[posgenre]==True):
                                        bRock.configure(image=rock_icon)
                                else:
                                        bRock.configure(image=rock_black)                         
                        if(posgenre==2):
                                if(genres_selected[posgenre]==True):
                                        bJazz.configure(image=jazz_icon)
                                else:
                                        bJazz.configure(image=jazz_black) 
                        if(posgenre==3):
                                if(genres_selected[posgenre]==True):
                                        bPop.configure(image=pop_icon)
                                else:
                                        bPop.configure(image=pop_black) 
                        if(posgenre==4):
                                if(genres_selected[posgenre]==True):
                                        bClassic.configure(image=classic_icon)
                                else:
                                        bClassic.configure(image=classic_black)
                        if(posgenre==5):
                                if(genres_selected[posgenre]==True):
                                        bReggaeton.configure(image=reggaeton_icon)
                                else:
                                        bReggaeton.configure(image=reggaeton_black)
                        if(posgenre==6):
                                if(genres_selected[posgenre]==True):
                                        bTrap.configure(image=trap_icon)
                                else:
                                        bTrap.configure(image=trap_black)
                        if(posgenre==7):
                                if(genres_selected[posgenre]==True):
                                        bOthers.configure(image=other_icon)
                                else:
                                        bOthers.configure(image=other_black)                                                 

                bElectronic.configure(command=lambda:changeLightDark(0))
                bRock.configure(command=lambda:changeLightDark(1))
                bJazz.configure(command=lambda:changeLightDark(2))
                bPop.configure(command=lambda:changeLightDark(3))
                bClassic.configure(command=lambda:changeLightDark(4))
                bReggaeton.configure(command=lambda:changeLightDark(5))
                bTrap.configure(command=lambda:changeLightDark(6))
                bOthers.configure(command=lambda:changeLightDark(7))

                Button(
                        editdiskwndw,
                        background="grey",
                        text="Cancelar",
                        font="Verdana 15",
                        command=lambda:editdiskwndw.destroy()
                ).grid(column=4, row=8)

                bSave=Button(
                        editdiskwndw,
                        background="#00bf36",
                        text="Guardar",
                        font="Verdana 15"
                )
                bSave.grid(column=1, row=8)

                def saveAndExit(posgenre): 
                        newName=inputNewName.get("1.0", "end-1c")
                        newYear=inputNewYear.get("1.0", "end-1c")
                        if(newName==""):
                                newName=oldName
                        if(newYear==""):
                                newYear=oldYear

                        if(newYear.isnumeric()==False):
                                newYear=oldYear
                        else:        
                                if(int(newYear)>int(year) or int(newYear)<0):
                                        newYear=oldYear

                        root[pos_in_audiolibrary].set('nombre', newName)
                        root[pos_in_audiolibrary].set('anho', newYear)

                        aux=True
                        while (aux==True):
                                aux=False
                                for se in root[pos_in_audiolibrary]:
                                        if(se.tag=="genero"):
                                                root[pos_in_audiolibrary].remove(se)
                                                aux=True
                        tree.write(filename)

                        if(posgenre[0]==True):
                                genre=ET.SubElement(root[pos_in_audiolibrary], 'genero')
                                genre.text="electronic"
                        if(posgenre[1]==True):
                                genre2=ET.SubElement(root[pos_in_audiolibrary], 'genero')
                                genre2.text="rock"                       
                        if(posgenre[2]==True):
                                genre3=ET.SubElement(root[pos_in_audiolibrary], 'genero')
                                genre3.text="jazz"
                        if(posgenre[3]==True):
                                genre4=ET.SubElement(root[pos_in_audiolibrary], 'genero')
                                genre4.text="pop"
                        if(posgenre[4]==True):
                                genre5=ET.SubElement(root[pos_in_audiolibrary], 'genero')
                                genre5.text="classic"
                        if(posgenre[5]==True):
                                genre6=ET.SubElement(root[pos_in_audiolibrary], 'genero')
                                genre6.text="reggaeton"
                        if(posgenre[6]==True):
                                genre7=ET.SubElement(root[pos_in_audiolibrary], 'genero')
                                genre7.text="trap"
                        if(posgenre[7]==True):
                                genre8=ET.SubElement(root[pos_in_audiolibrary], 'genero')
                                genre8.text="other"

                        tree.write(filename)
                        editdiskwndw.destroy()
                        diskwndw.destroy()
                        disckInferface(pos, False)



                bSave.configure(command=lambda:saveAndExit(genres_selected))
                                


        bedit.configure(command=editDiskInteface)        

        #it shows all the artist in the disc and allow to delete it
        def showArtistsInDisk():
                global pageArtists, showed
                artistwndw = Toplevel(diskwndw)
                artistwndw.title("Artistas")
                artistwndw.geometry("300x400")
                artistwndw['bg']='black'

                Label(
                        artistwndw,
                        background="black",
                        text="Artistas",
                        fg="#00bf36",
                        font="Verdana 30"
                ).grid(row=0, column=0, pady=30)

                badd_artists=Button(
                        artistwndw,
                        background="black",
                        image=add_artist_icon,
                        width=30,
                        height=30
                )
                badd_artists.grid(row=0, column=1)

                a1=Label(
                        artistwndw,
                        background="black",
                        text="Artistas",
                        fg="#00bf36",
                        font="Verdana 20"
                )
                a1.grid(row=1, column=0)

                b1=Button(
                        artistwndw,
                        height=45,
                        width=45,
                        image=delete_45_icon,
                        background="black"
                )

                b1.grid(row=1, column=1)

                a2=Label(
                        artistwndw,
                        background="black",
                        text="Artistas",
                        fg="#00bf36",
                        font="Verdana 20"
                )
                a2.grid(row=2, column=0)

                b2=Button(
                        artistwndw,
                        height=45,
                        width=45,
                        image=delete_45_icon,
                        background="black"
                )
                b2.grid(row=2, column=1)

                a3=Label(
                        artistwndw,
                        background="black",
                        text="Artistas",
                        fg="#00bf36",
                        font="Verdana 20"
                )
                a3.grid(row=3, column=0)

                b3=Button(
                        artistwndw,
                        height=45,
                        width=45,
                        image=delete_45_icon,
                        background="black"
                )
                b3.grid(row=3, column=1)

                a4=Label(
                        artistwndw,
                        background="black",
                        text="Artistas",
                        fg="#00bf36",
                        font="Verdana 20"
                )
                a4.grid(row=4, column=0)

                b4=Button(
                        artistwndw,
                        height=45,
                        width=45,
                        image=delete_45_icon,
                        background="black"
                )
                b4.grid(row=4, column=1)

                bRight2=Button(
                        artistwndw,
                        image=arrow_icon,
                        width=30,
                        height=30
                )
                bRight2.grid(column=1, row=5, pady=30)

                bLeft2=Button(
                        artistwndw,
                        image=arrowLeft_icon,
                        width=30,
                        height=30
                )
                bLeft2.grid(column=0, row=5, sticky="w", pady=30, padx=10)

                artists=[]
                numArtist=0
                for artista in root[pos_in_audiolibrary].iter('artista'):
                        numArtist+=1
                        artists.append(artista.text)
                
                
                pageArtists=0
                showed=[0, 1, 2, 3]
                def changePage():
                        global pageArtists
                        global showed
                        showed=[pageArtists*4, (pageArtists*4)+1, (pageArtists*4)+2, (pageArtists*4)+3]
                        if numArtist<=((pageArtists*4)+4):
                                bRight2.grid_forget()
                        else:
                                bRight2.grid(column=1, row=5, pady=30)
                        
                        if pageArtists==0:
                                bLeft2.grid_forget()
                        else:
                                bLeft2.grid(column=0, row=5, sticky="w", pady=30, padx=10)

                        if(numArtist>showed[0]):
                                a1.grid(row=1, column=0)
                                b1.grid(row=1, column=1)
                                a1.configure(text=artists[pageArtists*4])
                        else:
                                a1.grid_forget()
                                b1.grid_forget()

                        if(numArtist>showed[1]):
                                a2.grid(row=2, column=0)
                                b2.grid(row=2, column=1)
                                a2.configure(text=artists[(pageArtists*4)+1])
                        else:
                                a2.grid_forget()
                                b2.grid_forget()

                        if(numArtist>showed[2]):
                                a3.grid(row=3, column=0)
                                b3.grid(row=3, column=1)
                                a3.configure(text=artists[(pageArtists*4)+2])
                        else:
                                a3.grid_forget()
                                b3.grid_forget()

                        if(numArtist>showed[3]):
                                a4.grid(row=4, column=0)
                                b4.grid(row=4, column=1)
                                a4.configure(text=artists[(pageArtists*4)+3])
                        else:
                                a4.grid_forget()
                                b4.grid_forget()                       

                changePage()
                def pageRight():
                        global pageArtists
                        pageArtists+=1
                        changePage()
                def pageLeft():
                        global pageArtists
                        pageArtists-=1
                        changePage()
                bRight2.configure(command=pageRight)
                bLeft2.configure(command=pageLeft)

                def deleteArtist(arPos):
                        count_a=0
                        for artista in root[pos_in_audiolibrary].iter('artista'):
                                if(count_a == arPos):
                                        root[pos_in_audiolibrary].remove(artista)
                                        tree.write(filename)
                                        break
                                count_a+=1
                        artistwndw.destroy()
                        showArtistsInDisk()                       

                b1.configure(command=lambda: deleteArtist(showed[0]))
                b2.configure(command=lambda: deleteArtist(showed[1]))
                b3.configure(command=lambda: deleteArtist(showed[2]))
                b4.configure(command=lambda: deleteArtist(showed[3]))

                def addArtists():
                        newartistwndw = Toplevel(artistwndw)
                        newartistwndw.title("Nuevo Artista")
                        newartistwndw.geometry("600x400")
                        newartistwndw['bg']='black'
                        Label(
                                newartistwndw,
                                text="Nombre del nuevo artista",
                                background="black",
                                fg="#00bf36",
                                font="Verdana 20"
                        ).grid(row=0, column=0)

                        inputartistname = Text(
                                newartistwndw,
                                height = 1,
                                width = 15,
                                font="Verdana 30"
                        )
                        inputartistname.grid(row=0, column=1, padx=10, pady=20)

                        def addArtistF():
                                n=inputartistname.get("1.0", "end-1c")
                                if(n!=""):
                                        artist=ET.SubElement(root[pos_in_audiolibrary], 'artista')
                                        artist.text=n
                                        tree.write(filename)

                                newartistwndw.destroy()
                                artistwndw.destroy()
                                showArtistsInDisk()

                        Button(
                              newartistwndw,
                              text="Añadir",
                              background="#00bf36",
                              font="Verdana 20",
                              command=addArtistF  
                        ).grid(row=1, column=0)

                        Button(
                              newartistwndw,
                              text="Cancelar",
                              background="grey",
                              font="Verdana 20",
                              command=lambda: newartistwndw.destroy()  
                        ).grid(row=1, column=1)                
                
                badd_artists.configure(command=addArtists)

        bmore_artists.configure(command=lambda:showArtistsInDisk())
        bedit1.configure(command=lambda:editSongInterface(0))
        bedit2.configure(command=lambda:editSongInterface(1))
        bedit3.configure(command=lambda:editSongInterface(2))
        bedit4.configure(command=lambda:editSongInterface(3))


"""
end ofsongs of the disks interface
***********************************************************************************************************************************************
"""       


list_d[0].configure(command=lambda:disckInferface(0, False))
list_d[1].configure(command=lambda:disckInferface(1, False))
list_d[2].configure(command=lambda:disckInferface(2, False))
list_d[3].configure(command=lambda:disckInferface(3, False))
list_d[4].configure(command=lambda:disckInferface(4, False))
list_d[5].configure(command=lambda:disckInferface(5, False))


wndw.mainloop()