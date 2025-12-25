# سیستم مدیریت کتابخانه دانشگاه - مستندات REST API

## 📌 اطلاعات پایه
- **Base URL:** `http://localhost:8081/api`
- **پورت:** 8081
- **فرمت پاسخ:** JSON
- **دیتابیس:** H2 (درون‌حافظه)
- **Console دیتابیس:** `http://localhost:8081/h2-console`

---

## 📋 لیست کامل APIها

### ۱. 🔐 احراز هویت (Authentication)

#### ثبت‌نام دانشجو
- **Endpoint:** `POST /auth/register`
- **توضیح:** ثبت‌نام دانشجوی جدید در سیستم
- **بدنه درخواست (Request Body):**
```json
{
  "username": "string (ضروری)",
  "password": "string (ضروری)"
}