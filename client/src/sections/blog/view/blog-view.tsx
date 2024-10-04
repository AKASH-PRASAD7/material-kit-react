import { useState, useCallback, useEffect } from 'react';

import Box from '@mui/material/Box';

import Grid from '@mui/material/Unstable_Grid2';
import Typography from '@mui/material/Typography';

import { _posts } from 'src/_mock';
import { DashboardContent } from 'src/layouts/dashboard';
import axios from 'axios';
import Cookies from 'js-cookie';
import { PostItem } from '../post-item';

// ----------------------------------------------------------------------
interface Blog {
  id: string;
  title: string;
  coverUrl: string;
  totalViews: number;
  description: string;
  totalShares: number;
  totalComments: number;
  totalFavorites: number;
  postedAt: string | number | null;
  name: string;
  avatarUrl: string;
}
export function BlogView() {
  const [blogs, setBlogs] = useState<Blog[] | undefined>(undefined);

  const fetchBlogs = useCallback(async () => {
    try {
      const response = await axios.get('http://localhost:9000/api/blogs', {
        headers: {
          roles: `${Cookies.get('role')}`,
        },
      });
      setBlogs(response.data);
    } catch (error) {
      console.error('Error fetching blogs', error.message);
    }
  }, []);

  useEffect(() => {
    fetchBlogs();
  }, [fetchBlogs]);

  return (
    <DashboardContent>
      <Box display="flex" alignItems="center" mb={5}>
        <Typography variant="h4" flexGrow={1}>
          Blogs
        </Typography>
      </Box>

      <Grid container spacing={3}>
        {blogs &&
          blogs.map((post, index) => {
            const latestPostLarge = index === 0;
            const latestPost = index === 1 || index === 2;

            return (
              <Grid
                key={post.id}
                xs={12}
                sm={latestPostLarge ? 12 : 6}
                md={latestPostLarge ? 6 : 3}
              >
                <PostItem post={post} latestPost={latestPost} latestPostLarge={latestPostLarge} />
              </Grid>
            );
          })}
      </Grid>
    </DashboardContent>
  );
}
