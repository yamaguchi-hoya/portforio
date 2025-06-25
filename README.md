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
BootStrap 5.3.3   
javascript
## サービスURL  
https://stock-ms-app-d4b767601d27.herokuapp.com/
## 機能一覧  
#### ・在庫管理  
在庫管理　TOP ![Image](https://github.com/user-attachments/assets/8c77cd57-04a6-43c7-a0c6-bb7c80db6051)  | 出荷登録![Image](https://github.com/user-attachments/assets/d18e99c3-7078-4d2c-be08-a7793710705a)
:---: | :---:
入荷登録![Image](https://github.com/user-attachments/assets/cc22c800-6b56-4c1f-abb5-b78940567d06)　| カテゴリー登録　![Image](https://github.com/user-attachments/assets/4925e0c4-aa02-4ce9-990f-92ebb491d19b)
商品登録　![Image](https://github.com/user-attachments/assets/7870fc72-bbda-49ad-97f2-a4c7657b503b) | 在庫一覧　![Image](https://github.com/user-attachments/assets/64df80ef-238a-4cac-a4a1-e27c839269bd)
出納履歴　![Image](https://github.com/user-attachments/assets/938f32d6-5494-47db-b5cb-457b47c20ad1) |
#### ・納品書管理  
納品書　TOP![Image](https://github.com/user-attachments/assets/1221e4bc-3c77-4a14-8c01-7b0a4c83e0d2) | 納品書作成![Image](https://github.com/user-attachments/assets/374e2b6d-4899-412b-b7ae-3baa15a02904)
:---: | :---:
納品履歴 ![Image](https://github.com/user-attachments/assets/4b04381d-31b9-4d30-9f4d-060c577fa80b) | 会社情報登録 ![Image](https://github.com/user-attachments/assets/817f9862-d896-4b22-b718-6015bfe75aa4)
## ER図
![Image](https://github.com/user-attachments/assets/1f363df3-e349-4398-9f33-49b3dfdd6683)
## 画面遷移図
![Image](https://github.com/user-attachments/assets/53687259-37a4-42f4-83bf-f789bdd05660)
