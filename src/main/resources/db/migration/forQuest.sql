select
    p1_0.bucket_id,
    p1_1.id,
    p1_1.name,
    p1_1.price,
    c1_0.id,
    c1_0.name,
    b1_0.id,
    b1_0.name
from
    bucket_products p1_0
        join
    (products p1_1
        left join
        category_products p1_2
        on p1_1.id=p1_2.product_id
        left join
        brand_products p1_3
     on p1_1.id=p1_3.product_id)
    on p1_1.id=p1_0.product_id
        left join
    categories c1_0
    on c1_0.id=p1_2.category_id
        left join
    brands b1_0
    on b1_0.id=p1_3.brand_id
where
    p1_0.bucket_id=1