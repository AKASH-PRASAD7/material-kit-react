import { Label } from 'src/components/label';
import { SvgColor } from 'src/components/svg-color';
import Cookies from 'js-cookie';

// ----------------------------------------------------------------------

const icon = (name: string) => (
  <SvgColor width="100%" height="100%" src={`/assets/icons/navbar/${name}.svg`} />
);
const role = Cookies.get('role');

export const navData = [
  {
    title: 'Dashboard',
    path: '/dashboard',
    icon: icon('ic-analytics'),
    show: role === 'admin' || role === 'operations',
  },
  {
    title: 'User',
    path: '/user',
    icon: icon('ic-user'),
    show: role === 'admin',
  },
  {
    title: 'Product',
    path: '/',
    icon: icon('ic-cart'),
    info: (
      <Label color="error" variant="inverted">
        +3
      </Label>
    ),
    show: true,
  },
  {
    title: 'Blog',
    path: '/blog',
    icon: icon('ic-blog'),
    show: role === 'sales' || role === 'admin' || role === 'operations',
  },
  {
    title: 'Sign in',
    path: '/sign-in',
    icon: icon('ic-lock'),
    show: true,
  },
];
