import { NavLink } from 'react-router-dom'

const linkClass = ({ isActive }: { isActive: boolean }) =>
  `px-4 py-2 rounded-full text-sm font-semibold transition-colors ${
    isActive
      ? 'bg-white/15 text-white'
      : 'text-slate-300 hover:text-white hover:bg-white/10'
  }`

export function NavBar() {
  return (
    <nav className="sticky top-0 z-40 border-b border-white/10 bg-slate-950 shadow-lg">
      <div className="mx-auto flex h-16 max-w-6xl items-center justify-between px-4 sm:px-6 lg:px-8">
        <NavLink to="/" className="font-display text-xl font-bold tracking-tight text-white">
          Case Tracker
        </NavLink>
        <div className="flex gap-1">
          <NavLink to="/cases" className={linkClass}>
            Cases
          </NavLink>
          <NavLink to="/parties" className={linkClass}>
            Parties
          </NavLink>
        </div>
      </div>
    </nav>
  )
}
