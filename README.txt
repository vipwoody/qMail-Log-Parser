How to run jar file
===================
$ java -jar qmailLogParser.jar


1. Coding Standard
==================
Java SE 6: http://java.sun.com/javase/6/docs/

2. Description of message delivery
==================================
The transfer of an email begins with the mail user agent (MUA) injecting the message into the
mail system. The MUA runs a program hat hands the message off to the message transfer agent (MTA).
A series of checks are carried out by the MTA to determine if it accepts the message or not. If the checks
are passed the message becomes the MTA’s responsibility.
MTA systems can function under different protocols, but generally the triggered MTA will
inspect the message header and determine where to send the email. MTA #1 then opens a SMTP
connection (generally port 25) to MTA #2 and forwards the message. The SMTP dialogue requires two
parts: an envelop with the recipient’s address and the return address, as well as the header and body of the
message.
Tcpserver decides whether to allow the computer to connect. Upon receiving the message MTA
#2 runs the program which chooses to accept / reject the incoming message. CHKUSR checks whether the
recipient’s address is valid and whether the connection is allowed before initializing qmail-smtpd, which
will process the message from there.
Qmail-smtpd checks to see if the message should be relayed and if not opens qmail-queue to store
the mail to the disk. Next qmail-send is run and looks at the envelope recipient to see if the @domain.com
is a local or remote delivery. If the recipient is local then it will trigger qmail-lspawn which looks at 
themail box name (ex: nina@concordia.ca). The name is checked to make sure nina is a qmail-user, the
account exists and her uid is nonzero. If the name checks out then control is given over to nina and 
qmaillocal is run. Qmail-local writes the message to the mailbox in mbox format.
However if qmail-send determines that the @domain.com is a remote delivery it will run qmail-rspawn
which in turns opens qmail-remote. Qmail-remote looks up the host name and finds its DNS MX/A which
is connected to by SMTP effectively beginning the MTA process again.
During the qmail-queue process the simscan program is run which checks the incoming message
for spam and viruses allowing the SMTP service to reject it if necessary. Simscan runs a few processes
including spamassassin to detect and reject spam through dcc, pyzor, razor …etc. and clamAV, which will
investigate messages for viruses. Depending on the implementation, these processes will either accept the
email or reject it; deleting it instantly or bouncing it back to sender. Please consult the diagram on the last
page of the document.

3. Tracing a message through our program The program begins by reading the logfiles and parsing their
data to store in  separate classes. Since we had difficulty linking the logs together, a message that 
appears in multiple logs will come up in multiple data objects that areunconnected. For
instance, if a message is tagged as being spam it will appear in the spamd log and be read by thespamReader class.
SpamReader will store the message’s important details in a messageInput object which is then added to a public
hash table inside globalClass. globalClass stores all the message objects so that they can be accessed for queries. 
So effectively, messages are picked up by the program in log files, parsed into object files and then sent to 
globalClass to be stored in hash tables.
