# 在庫管理システム
## 概要
このシステムは「在庫管理」と「納品書作成・管理」の２つの機能を有し、この２つは連動しています。
## 開発した背景
前職で商品の在庫や納品管理をしていた経験から、「こんなものが欲しかった」という思いから作成しました。  
当時は在庫管理Googleスプレッドシート、納品書はExcelを使用していましたが、  
・誰がいつ変更を加えたかわからない  
・各業務のツールが分散しているので、記録を忘れる可能性がある  
・納品書データを一元管理するために、業務工程が増える  
などの問題がありました。  
これらの問題を解消できるような、各機能を一つにまとめて業務を簡略化できるシステムを目指し、作成しました。
## 使用技術
java 17  
Spring Boot 3.5.0  
html  
css  
javascript
## 機能一覧  
#### ・在庫管理  
出荷登録 ![Image](https://github.com/user-attachments/assets/9fb3c545-c44b-4e12-b351-d730df45d4fa)  | 入荷登録 ![Image](https://github.com/user-attachments/assets/f5e9662d-4010-47a7-b976-f5268ca3ad53)
:---: | :---:
カテゴリ管理 ![Image](https://github.com/user-attachments/assets/9f59a674-68f2-4382-b1d5-f47b96192bf7)　| 商品管理　![Image](https://github.com/user-attachments/assets/1aa1126a-a000-4528-8fbc-1d937310b66f)
在庫一覧　![Image](https://github.com/user-attachments/assets/f0ab1e70-5c9b-4f9b-aff3-39bf9e8d24a3) | 出納履歴　![Image](https://github.com/user-attachments/assets/5f59d839-66a9-4641-b567-926dcae1c944)
#### ・納品書管理  
納品書作成 ![Image](https://github.com/user-attachments/assets/0063004a-9c05-4baf-951d-12cf21d2d821) | 納品履歴 ![Image](https://github.com/user-attachments/assets/9299736e-bb52-4ed0-96ad-7273b7f1c90b)
:---: | :---:
会社情報登録 ![Image](https://github.com/user-attachments/assets/75733083-4f81-4855-972d-8c6dcff7e1b2)
## ER図
![Image](https://github.com/user-attachments/assets/1f363df3-e349-4398-9f33-49b3dfdd6683)
## 画面遷移図
![Image](https://github.com/user-attachments/assets/fe6018e3-8bfa-4aa2-b426-237ded477fd7)
