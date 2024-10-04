import { useState, useCallback, useEffect } from 'react';

import Grid from '@mui/material/Unstable_Grid2';
import Typography from '@mui/material/Typography';

import { _products } from 'src/_mock';
import { DashboardContent } from 'src/layouts/dashboard';
import axios from 'axios';

import { ProductItem } from '../product-item';

interface Product {
  id: string;
  name: string;
  price: number;
  status: string;
  coverUrl: string;
  colors: string[];
  priceSale: number | null;
}

export function ProductsView() {
  const [products, setProducts] = useState<Product[] | undefined>(undefined);

  const fetchProducts = useCallback(async () => {
    try {
      const response = await axios.get('http://localhost:9000/api/products');
      setProducts(response.data);
    } catch (error) {
      console.error('Error fetching products', error.message);
    }
  }, []);

  useEffect(() => {
    fetchProducts();
  }, [fetchProducts]);

  return (
    <DashboardContent>
      <Typography variant="h4" sx={{ mb: 5 }}>
        Products
      </Typography>

      <Grid container spacing={3}>
        {products &&
          products.map((product: Product) => (
            <Grid key={product.id} xs={12} sm={6} md={3}>
              <ProductItem product={product} />
            </Grid>
          ))}
      </Grid>
    </DashboardContent>
  );
}
