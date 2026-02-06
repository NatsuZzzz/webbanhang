USE FoodStore;
GO

/* =========================
   1. VIEW CHO USER (CHỈ XEM)
========================= */
IF OBJECT_ID('vw_User_Products','V') IS NOT NULL
    DROP VIEW vw_User_Products;
GO

CREATE VIEW vw_User_Products
AS
SELECT 
    p.id,
    p.name,
    p.price,
    p.image_url,
    p.description,
    c.name AS category_name
FROM Products p
JOIN Categories c ON p.category_id = c.id
WHERE p.is_available = 1;
GO


/* =========================
   2. VIEW CHO ADMIN (FULL)
========================= */
IF OBJECT_ID('vw_Admin_Products','V') IS NOT NULL
    DROP VIEW vw_Admin_Products;
GO

CREATE VIEW vw_Admin_Products
AS
SELECT * FROM Products;
GO


/* =========================
   3. TRIGGER CHẶN USER CRUD
   role_id = 1 → ADMIN
   role_id = 2 → USER
========================= */

-- INSERT
CREATE OR ALTER TRIGGER trg_Product_Insert
ON Products
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @role_id INT;

    SELECT @role_id = role_id
    FROM Users
    WHERE email = SUSER_NAME(); -- email = username login

    IF @role_id = 1
    BEGIN
        INSERT INTO Products(name, category_id, price, image_url, description, is_available)
        SELECT name, category_id, price, image_url, description, is_available
        FROM inserted;
    END
    ELSE
    BEGIN
        RAISERROR (N'Bạn không có quyền thêm sản phẩm', 16, 1);
    END
END;
GO


-- UPDATE
CREATE OR ALTER TRIGGER trg_Product_Update
ON Products
INSTEAD OF UPDATE
AS
BEGIN
    DECLARE @role_id INT;

    SELECT @role_id = role_id
    FROM Users
    WHERE email = SUSER_NAME();

    IF @role_id = 1
    BEGIN
        UPDATE p
        SET 
            p.name = i.name,
            p.category_id = i.category_id,
            p.price = i.price,
            p.image_url = i.image_url,
            p.description = i.description,
            p.is_available = i.is_available
        FROM Products p
        JOIN inserted i ON p.id = i.id;
    END
    ELSE
    BEGIN
        RAISERROR (N'Bạn không có quyền sửa sản phẩm', 16, 1);
    END
END;
GO


-- DELETE
CREATE OR ALTER TRIGGER trg_Product_Delete
ON Products
INSTEAD OF DELETE
AS
BEGIN
    DECLARE @role_id INT;

    SELECT @role_id = role_id
    FROM Users
    WHERE email = SUSER_NAME();

    IF @role_id = 1
    BEGIN
        DELETE p
        FROM Products p
        JOIN deleted d ON p.id = d.id;
    END
    ELSE
    BEGIN
        RAISERROR (N'Bạn không có quyền xóa sản phẩm', 16, 1);
    END
END;
GO
