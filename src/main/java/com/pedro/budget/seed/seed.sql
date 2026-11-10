CREATE EXTENSION IF NOT EXISTS pgcrypto;

DO $$
DECLARE
v_user_id            UUID;
    v_cat_food_id        UUID;
    v_cat_shopping_id    UUID;
    v_cat_income_id      UUID;
    v_cat_finances_id    UUID;
    v_person_me_id       UUID;
    v_person_maria_id    UUID;
    v_person_joao_id     UUID;
    v_bank_nubank_id     UUID;
    v_bank_itau_id       UUID;
    v_card_nubank_id     UUID;
    v_card_itau_id       UUID;
    v_credit_purchase_id UUID;
    v_tx_id              UUID;
    i                    INTEGER;
BEGIN
    -- 1. Create (or reuse) test user: username = 'test_user', password = 'Senha@2026'
    SELECT id INTO v_user_id
    FROM users
    WHERE username = 'test_user'
    LIMIT 1;
    IF v_user_id IS NOT NULL THEN
        RAISE NOTICE 'test_user already exists; skipping seed.';
        RETURN;
    END IF;
    INSERT INTO users (id, username, password, role)
    VALUES (
               gen_random_uuid(),
               'test_user',
               crypt('Senha@2026', gen_salt('bf', 10)),
               'USER'
           )
    RETURNING id INTO v_user_id;

    -- 2. Categories
INSERT INTO categories (id, title, type, user_id)
VALUES (gen_random_uuid(), 'Groceries', 'FOOD', v_user_id)
    RETURNING id INTO v_cat_food_id;

INSERT INTO categories (id, title, type, user_id)
VALUES (gen_random_uuid(), 'Shopping', 'SHOPPING', v_user_id)
    RETURNING id INTO v_cat_shopping_id;

INSERT INTO categories (id, title, type, user_id)
VALUES (gen_random_uuid(), 'Salary', 'INCOME', v_user_id)
    RETURNING id INTO v_cat_income_id;

INSERT INTO categories (id, title, type, user_id)
VALUES (gen_random_uuid(), 'Credit card payment', 'FINANCES', v_user_id)
    RETURNING id INTO v_cat_finances_id;

-- 3. Persons
INSERT INTO person (id, name, email, user_id)
VALUES (gen_random_uuid(), 'Me', 'test_user@example.com', v_user_id)
    RETURNING id INTO v_person_me_id;

INSERT INTO person (id, name, email, user_id)
VALUES (gen_random_uuid(), 'Maria Silva', 'maria.silva@example.com', v_user_id)
    RETURNING id INTO v_person_maria_id;

INSERT INTO person (id, name, email, user_id)
VALUES (gen_random_uuid(), 'João Souza', 'joao.souza@example.com', v_user_id)
    RETURNING id INTO v_person_joao_id;

-- 4. Bank Accounts (initial_balance in cents)
INSERT INTO bank_account (id, type, description, initial_balance, user_id)
VALUES (gen_random_uuid(), 'NUBANK', 'Conta Nubank Principal', 250000, v_user_id)
    RETURNING id INTO v_bank_nubank_id;

INSERT INTO bank_account (id, type, description, initial_balance, user_id)
VALUES (gen_random_uuid(), 'ITAU', 'Conta Itaú Reserva', 1000000, v_user_id)
    RETURNING id INTO v_bank_itau_id;

-- 5. Cards
INSERT INTO card (id, description, card_number, due_day, closing_day, bank_account_id, user_id)
VALUES (gen_random_uuid(), 'Cartão Nubank Platinum', '4321', 10, 3, v_bank_nubank_id, v_user_id)
    RETURNING id INTO v_card_nubank_id;

INSERT INTO card (id, description, card_number, due_day, closing_day, bank_account_id, user_id)
VALUES (gen_random_uuid(), 'Cartão Itaú Black', '8765', 15, 8, v_bank_itau_id, v_user_id)
    RETURNING id INTO v_card_itau_id;

-- 6. Transactions (amounts in cents)
-- 6a. Income
INSERT INTO transactions (id, kind, amount, description, date, transaction_type, user_id, category_id, bank_account_id, person_id)
VALUES (gen_random_uuid(), 'INCOME', 500000, 'Salário Mensal', DATE '2026-10-05', 'INCOME', v_user_id, v_cat_income_id, v_bank_nubank_id, v_person_me_id)
    RETURNING id INTO v_tx_id;

INSERT INTO transactions_incomes (id, income_type)
VALUES (v_tx_id, 'TED');

-- 6b. Expense (matches q=15200)
INSERT INTO transactions (id, kind, amount, description, date, transaction_type, user_id, category_id, bank_account_id, person_id)
VALUES (gen_random_uuid(), 'EXPENSE', 15200, 'Tênis Nike Traineer', DATE '2026-10-12', 'EXPENSE', v_user_id, v_cat_shopping_id, v_bank_nubank_id, v_person_me_id)
    RETURNING id INTO v_tx_id;

INSERT INTO transactions_expenses (id, expense_type)
VALUES (v_tx_id, 'PIX');

-- 6c. Expense (matches q=nubank alongside BankAccount and Card)
INSERT INTO transactions (id, kind, amount, description, date, transaction_type, user_id, category_id, bank_account_id, person_id)
VALUES (gen_random_uuid(), 'EXPENSE', 85000, 'Fatura Nubank', DATE '2026-10-18', 'EXPENSE', v_user_id, v_cat_finances_id, v_bank_itau_id, v_person_maria_id)
    RETURNING id INTO v_tx_id;

INSERT INTO transactions_expenses (id, expense_type)
VALUES (v_tx_id, 'BOLETO');

-- 6d. Expense (Groceries)
INSERT INTO transactions (id, kind, amount, description, date, transaction_type, user_id, category_id, bank_account_id, person_id)
VALUES (gen_random_uuid(), 'EXPENSE', 32450, 'Supermercado Mensal', DATE '2026-10-20', 'EXPENSE', v_user_id, v_cat_food_id, v_bank_nubank_id, v_person_joao_id)
    RETURNING id INTO v_tx_id;

INSERT INTO transactions_expenses (id, expense_type)
VALUES (v_tx_id, 'PIX');

-- 6e. Credit Purchase (3 installments)
INSERT INTO credit_purchase (id, card_id, total_installments)
VALUES (gen_random_uuid(), v_card_nubank_id, 3)
    RETURNING id INTO v_credit_purchase_id;

FOR i IN 1..3 LOOP
        INSERT INTO transactions (id, kind, amount, description, date, transaction_type, user_id, category_id, bank_account_id, person_id)
        VALUES (
            gen_random_uuid(),
            'CREDIT_EXPENSE',
            40000,
            'Compra Notebook',
            (DATE '2026-10-25' + ((i - 1) * INTERVAL '1 month'))::DATE,
            'CREDIT_EXPENSE',
            v_user_id,
            v_cat_shopping_id,
            v_bank_nubank_id,
            v_person_me_id
        )
        RETURNING id INTO v_tx_id;

INSERT INTO transactions_credit_expenses (id, purchase_id, installment_number, installment_total)
VALUES (v_tx_id, v_credit_purchase_id, i, 3);
END LOOP;
END $$;
