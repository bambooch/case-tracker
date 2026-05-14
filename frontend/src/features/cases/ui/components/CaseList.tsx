import type { CaseSummary } from '../../domain/caseSummary'
import { primaryButtonClasses } from '../caseTheme'
import { CaseListItem } from './CaseListItem'

type CaseListProps = {
  cases: CaseSummary[]
  onDelete: (caseId: number) => Promise<void>
  onCreateNew: () => void
}

export function CaseList({ cases, onDelete, onCreateNew }: CaseListProps) {
  return (
    <section className="rounded-[2rem] border border-white/70 bg-white/75 p-6 shadow-[0_30px_80px_rgba(15,23,42,0.12)] backdrop-blur sm:p-8">
      <div className="flex flex-col gap-2 sm:flex-row sm:items-end sm:justify-between">
        <div>
          <p className="text-sm font-semibold uppercase tracking-[0.3em] text-slate-500">Board</p>
          <h2 className="mt-2 font-display text-3xl text-slate-950">Current cases</h2>
        </div>
        <button className={primaryButtonClasses} type="button" onClick={onCreateNew}>
          + New case
        </button>
      </div>

      <ul className="mt-6 space-y-3 p-0" style={{ listStyleType: 'none', margin: 0, padding: 0 }}>
        {cases.map((caseSummary) => (
          <CaseListItem key={caseSummary.id} caseSummary={caseSummary} onDelete={onDelete} />
        ))}
      </ul>

      {cases.length === 0 ? (
        <div className="mt-6 rounded-[1.75rem] border border-dashed border-stone-300 bg-stone-50 px-6 py-10 text-center text-slate-500">
          No cases yet.{' '}
          <button type="button" className="font-semibold text-orange-700 underline hover:text-orange-900" onClick={onCreateNew}>
            Create the first one
          </button>{' '}
          to start the board.
        </div>
      ) : null}
    </section>
  )
}
