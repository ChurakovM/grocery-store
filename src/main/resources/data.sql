-- Vegetable discounts table
INSERT INTO vegetable_discounts (min_weight_grams, max_weight_grams, discount_percent)
VALUES
    (0, 100, 5.0),
    (101, 500, 7.0),
    (501, NULL, 10.0);

-- Beer discounts table
INSERT INTO beer_discounts (beer_type, pack_price, pack_size)
VALUES
    ('Belgium', 3.0, 6),
    ('Dutch', 2.0, 6),
    ('German', 4.0, 6);

-- Bread discounts table
INSERT INTO bread_discounts (age_days, buy_quantity, take_quantity)
VALUES
    (3, 1, 2),
    (6, 1, 3);

-- Prices table
INSERT INTO prices (product_type, unit_price, unit)
VALUES
    ('BREAD', 1.0, 'piece'),
    ('VEGETABLE', 1.0, '100g'),
    ('BEER', 0.5, 'bottle');