import stashy

f=open("projectName.txt", "r")
if f.mode == 'r':
    contents = f.read()
    bitbucket = stashy.connect("http://localhost:7990", "aydinnyunus", "112358")
    bitbucket.projects.create(contents, contents)


