select
    p1_0.id,
    p1_0.name,
    p1_0.price,
    p1_1.category_id

from
    products p1_0
        left join
    category_products p1_1
    on p1_0.id=p1_1.product_id