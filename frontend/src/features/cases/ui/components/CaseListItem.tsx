import { useState } from 'react'
import { Link } from 'react-router-dom'

import type { CaseSummary } from '../../domain/caseSummary'
import { attentionBadgeClasses, attentionLabels, dangerButtonClasses, primaryButtonClasses, secondaryButtonClasses, statusBadgeClasses } from '../caseTheme'

type CaseListItemProps = {
  caseSummary: CaseSummary
  onDelete: (caseId: number) => Promise<void>
}

export function CaseListItem({ caseSummary, onDelete }: CaseListItemProps) {
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false)

  return (
    <li className="group rounded-[1.75rem] border border-stone-200 bg-stone-50/80 p-5 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md">
      <div className="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
        <Link to={`/cases/${caseSummary.id}`} className="flex-1 text-left">
          <div className="flex flex-wrap items-center gap-2">
            <h2 className="font-display text-xl text-slate-950 transition-colors group-hover:text-slate-700 sm:text-2xl">
              {caseSummary.title}
            </h2>
            <span
              className={`rounded-full px-3 py-1 text-xs font-semibold uppercase tracking-[0.18em] ${statusBadgeClasses[caseSummary.status] ?? 'bg-slate-200 text-slate-800 ring-1 ring-inset ring-slate-300'}`}
            >
              {caseSummary.status}
            </span>
            <span
              className={`rounded-full px-3 py-1 text-xs font-semibold uppercase tracking-[0.16em] ${attentionBadgeClasses[caseSummary.attentionLevel] ?? 'bg-stone-200 text-stone-700 ring-1 ring-inset ring-stone-300'}`}
            >
              {attentionLabels[caseSummary.attentionLevel] ?? caseSummary.attentionLevel}
            </span>
          </div>
          <p className="mt-1 text-sm text-slate-400">Predmet #{caseSummary.id}</p>
        </Link>

        <div className="flex shrink-0 flex-wrap items-center gap-2 lg:justify-end">
          {showDeleteConfirm ? (
            <>
              <span className="text-sm font-semibold text-rose-700">Obrisati?</span>
              <button
                type="button"
                className={dangerButtonClasses}
                onClick={() => void onDelete(caseSummary.id).then(() => setShowDeleteConfirm(false))}
              >
                Potvrdi
              </button>
              <button type="button" className={secondaryButtonClasses} onClick={() => setShowDeleteConfirm(false)}>
                Otkaži
              </button>
            </>
          ) : (
            <>
              <Link to={`/cases/${caseSummary.id}`} className={primaryButtonClasses}>
                Pogledaj detalje →
              </Link>
              <button type="button" className={dangerButtonClasses} onClick={() => setShowDeleteConfirm(true)}>
                Obriši
              </button>
            </>
          )}
        </div>
      </div>
    </li>
  )
}
