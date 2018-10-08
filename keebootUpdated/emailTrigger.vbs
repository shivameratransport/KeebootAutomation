Dim ToAddress
Dim FromAddress
Dim MessageSubject
Dim MyTime
Dim MessageBody
Dim MessageAttachment
Dim ol, ns, newMail
Dim objFSO
Set objFSO = CreateObject("Scripting.FileSystemObject")
with CreateObject("Scripting.Filesystemobject") 
Dim CurrentDirectory
MyTime = Now
CurrentDirectory = objFSO.GetAbsolutePathName(".")

ToAddress1 = "jshah@mgmresorts.com"
MessageSubject = "Auto Stats " & MyTime
MessageBody = "Stats Attached" & vbCrLf & "Produced at " & MyTime

MessageAttachment = CurrentDirectory & "\target\surefire-reports\automation-email-report.html"
Set ol = WScript.CreateObject("Outlook.Application")
Set ns = ol.getNamespace("MAPI")
Set newMail = ol.CreateItem(olMailItem)
newMail.Subject = MessageSubject
newMail.Body = MessageBody
newMail.HTMLBody = .OpenTextFile(CurrentDirectory & "\target\surefire-reports\automation-email-report.html").ReadAll() 
newMail.RecipIents.Add(ToAddress1)
newMail.Attachments.Add(MessageAttachment)
newMail.Send
End with