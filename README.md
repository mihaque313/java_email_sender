# This program can be used to send automated email with or without attachments, with Body as text or HTML

Pass arguments to program as follows:

## CASE 1: HTML body with attachment
-html <path/to/file/to/be/put/as/body.html> <comma_seperated_reciepents_email_addresses> <mail_subject> </path/to/file(s)/for/attachment1,/path/to/file(s)/for/attachment2,/path/to/file(s)/for/attachmentN>

## CASE 2: HTML body without attachment
-html <path/to/file/to/be/put/as/body.html> <comma_seperated_reciepents_email_addresses> <mail_subject> 

## CASE 3: TEXT body with attachment
<path/to/file/to/be/put/as/body.html> <comma_seperated_reciepents_email_addresses> <mail_subject> </path/to/file(s)/for/attachment1,/path/to/file(s)/for/attachment2,/path/to/file(s)/for/attachmentN>

or

-text <path/to/file/to/be/put/as/body.html> <comma_seperated_reciepents_email_addresses> <mail_subject> </path/to/file(s)/for/attachment1,/path/to/file(s)/for/attachment2,/path/to/file(s)/for/attachmentN>

## CASE 1: TEXT body without attachment
<path/to/file/to/be/put/as/body.html> <comma_seperated_reciepents_email_addresses> <mail_subject> 

or 

-text <path/to/file/to/be/put/as/body.html> <comma_seperated_reciepents_email_addresses> <mail_subject> 