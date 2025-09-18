#專案簡介

這是FOR 面試用的考題答案

一個基於 Spring Boot 的 RESTful API 專案，主要功能是提供加密貨幣與法幣的匯率資訊。專案會定期從 CoinDesk API 取得最新匯率，並將其儲存在資料庫中。同時，它也提供了標準的 CRUD (Create, Read, Update, Delete) 功能，讓使用者可以手動管理匯率資料。

技術棧
後端框架: Spring Boot

資料庫: H2 Database (內嵌式資料庫)

API 呼叫: WebClient (Spring WebFlux)

測試框架: JUnit 5, Mockito

依賴管理: Maven

環境要求
Java 8 或更高版本

Maven 3.2+

如何啟動
複製專案：


git clone [你的專案 URL]
cd [你的專案資料夾]
啟動應用程式：

./mvnw spring-boot:run
應用程式啟動後，你可以在 http://localhost:8080 存取 API。

API 端點說明
專案提供以下 RESTful API：

1. 取得所有匯率資料
端點: GET /api/exchangeRate

說明: 查詢資料庫中所有儲存的匯率資料。

回應範例:

JSON

{
  "code": 200,
  "message": "Success",
  "data": [
    {
      "currency_code": "USD",
      "currency_name": "美元",
      "rate": 30.5,
      "updated_time": "2025-09-18T10:00:00"
    }
  ]
}

2. 新增一筆匯率資料
端點: POST /api/exchangeRate

說明: 新增一筆自訂的匯率資料。如果 currency_code 已存在，會拋出 DuplicateEntryException。

請求範例:

JSON

{
  "currency_code": "test_TWD",
  "currency_name": "新台幣",
  "rate": 30.0
}

3. 更新一筆匯率資料
端點: PUT /api/exchangeRate

說明: 更新一筆現有的匯率資料。如果 currency_code 不存在，會拋出 DataNotFindException。

請求範例:

JSON

{
  "currency_code": "USD",
  "currency_name": "美元 (更新)",
  "rate": 31.0
}

4. 刪除一筆匯率資料
端點: DELETE /api/exchangeRate

說明: 刪除指定 currency_code 的匯率資料。如果 currency_code 不存在，會拋出 DataNotFindException。

請求範例:

JSON

{
  "currency_code": "USD"
}

5. 呼叫 CoinDesk API 更新資料
端點: GET /api/exchangeRate/reflashExchangeRate

說明: 呼叫外部 CoinDesk API，取得最新匯率並更新至資料庫。

測試
專案使用 JUnit 5 和 Mockito 進行單元測試和整合測試。你可以運行以下 Maven 指令來執行所有測試：
./mvnw test




